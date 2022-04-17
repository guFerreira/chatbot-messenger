package br.com.ubots.estagio.BotMessenger.model.strategy;

import br.com.ubots.estagio.BotMessenger.model.WeatherForecast;
import br.com.ubots.estagio.BotMessenger.service.interfaces.WeatherService;
import br.com.ubots.estagio.BotMessenger.service.WeatherServiceImpl;
import com.google.cloud.dialogflow.v2.QueryResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherStrategy implements MessageCreationStrategy {
    private String receivedMessage;
    private WeatherService weatherService = new WeatherServiceImpl();

    public WeatherStrategy(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    @Override
    public String buildMessage(QueryResult queryResult) {
        String cityName = this.extractCityNameByReceivedMessage(queryResult);
        try{
            return this.createCityWeatherForecastMessage(cityName);
        }catch (Exception e){
            return this.buildFallBackMessageWhenCityNotFound(cityName);
        }
    }

    private String extractCityNameByReceivedMessage(QueryResult queryResult){
        return queryResult.getParameters().getFieldsMap().get("location")
                .getStructValue().getFieldsMap().get("city").getStringValue();
    }

    private String createCityWeatherForecastMessage(String cityName){
        WeatherForecast weatherForecastDTO = this.weatherService
                .getWeatherForecastByCityName(cityName);
        return this.buildWeatherForecastMessage(weatherForecastDTO);
    }

    private String buildWeatherForecastMessage(WeatherForecast weatherForecastDTO){
        String cityName = weatherForecastDTO.getCityName();
        String weatherDescription = weatherForecastDTO.getWeatherDescription();
        float temperatureInCelsius = this.transformKelvinToCelsius(weatherForecastDTO.getTemperatureInKelvin());
        return "Na cidade "+ cityName +", o clima atual é: " +
                weatherDescription + ".\nA temperatura atual está em " +
                this.formatTemperature(temperatureInCelsius) + "°C. "+ this.getEmojiThermometer();
    }

    private float transformKelvinToCelsius(float temperatureInKelvin){
        return temperatureInKelvin - 273f;
    }

    private String formatTemperature(float temperatureInCelsius){
        String temperature = String.format("%.2f", temperatureInCelsius);
        return temperature.replace(".",",");
    }

    private String getEmojiThermometer(){
        return "\uD83C\uDF21";
    }

    private String buildFallBackMessageWhenCityNotFound(String cityName){
        return "A cidade \""+cityName+"\" informada não foi encontrada," +
                " verifique se escreveu o nome da forma correta! :)";
    }

    @Override
    public boolean verifyIntents(String message) {
        return message.equals("#Clima");
    }
}
