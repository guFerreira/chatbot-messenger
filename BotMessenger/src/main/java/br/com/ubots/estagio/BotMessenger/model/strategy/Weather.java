package br.com.ubots.estagio.BotMessenger.model.strategy;

import br.com.ubots.estagio.BotMessenger.dto.WeatherForecastDTO;
import br.com.ubots.estagio.BotMessenger.service.WeatherService;
import br.com.ubots.estagio.BotMessenger.service.WeatherServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weather implements Response{
    private String receivedMessage;
    private WeatherService weatherService = new WeatherServiceImpl();

    public Weather(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    @Override
    public String buildMessage() {
        String cityName = this.extractCityNameByReceivedMessage(this.receivedMessage);
        try{
            WeatherForecastDTO weatherForecastDTO = this.weatherService.getWeatherForecastByCityName(cityName);
            return this.buildWeatherForecastMessage(weatherForecastDTO);
        }catch (Exception e){
            return this.buildCallBackCityNotFound(cityName);
        }

    }
    private String buildCallBackCityNotFound(String cityName){
        return "A cidade \""+cityName+"\" informada não foi encontrada," +
                " verifique se escreveu o nome da forma correta! :)";
    }

    private String extractCityNameByReceivedMessage(String receivedMessage){
        String regexIntents = "^[.]*[\\w\\s,À-úçÇ]*(cidade de|em|no) ([\\s\\w,À-úçÇ]{3,})[\\?\\.\\!]*";
        Pattern pattern = Pattern.compile(regexIntents);
        Matcher matcher = pattern.matcher(receivedMessage);
        String extratedCity = "";
        if(matcher.find()){
            extratedCity = matcher.group(2);
            System.out.println(extratedCity);
        }
        return extratedCity;
    }

    private String buildWeatherForecastMessage(WeatherForecastDTO weatherForecastDTO){
        String cityName = weatherForecastDTO.getName();
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

    @Override
    public boolean verifyIntents(String message) {
        String regexIntents = "^([\\w\\s,À-úçÇ]*)(clima|tempo|temperatura|)[\\w,À-úçÇ\\s]*(cidade de|em|no|cidade do) [\\s\\w,À-úçÇ]{3,}[\\?\\.\\!]*";
        return Pattern.matches(regexIntents,message);
    }
}
