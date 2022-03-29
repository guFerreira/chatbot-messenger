package br.com.ubots.estagio.BotMessenger.model.strategy;

import br.com.ubots.estagio.BotMessenger.dto.WeatherForecastDTO;
import br.com.ubots.estagio.BotMessenger.service.WeatherService;
import br.com.ubots.estagio.BotMessenger.service.WeatherServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

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
        WeatherForecastDTO weatherForecastDTO = this.weatherService.getWeatherForecastByCityName(cityName);

        return this.buildWeatherForecastMessage(weatherForecastDTO);
    }

    private String extractCityNameByReceivedMessage(String receivedMessage){
        //TODO implementar a lógica de extrair o nome da cidade.
        return "São Paulo";
    }


    private String buildWeatherForecastMessage(WeatherForecastDTO weatherForecastDTO){
        String cityName = weatherForecastDTO.getName();
        String weatherDescription = weatherForecastDTO.getWeatherDescription();
        float temperatureInCelsius = this.transformKelvinToCelsius(weatherForecastDTO.getTemperatureInKelvin());
        return "A cidade "+ cityName +" está com o clima " +
                weatherDescription + ". A temperatura atual está em " +
                temperatureInCelsius + " graus Celsius.";
    }

    private float transformKelvinToCelsius(float temperatureInKelvin){
        return temperatureInKelvin - 273f;
    }

    @Override
    public boolean verifyIntents(String message) {
        String regexIntents = "^([\\w\\sáàâãéèêíïóôõöúçñ]*)(clima|tempo|temperatura|)[\\w\\s]*(cidade de|em|no|cidade do) [\\s\\wáàâãéèêíïóôõöúçñ]{3,}";
        Pattern pattern = Pattern.compile(regexIntents);
        Matcher matcher = pattern.matcher(message);
        return matcher.find();
    }
}
