package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.model.WeatherForecast;
import br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecastDto;
import br.com.ubots.estagio.BotMessenger.service.interfaces.WeatherService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;

@Component
public class WeatherServiceWeatherbit{
    private static final String KEY_WEATHERBIT_API = "ee5c6ca8e6a640be82f757e159a1ee3b";
    private static final String URL_WEATHERBIT_DAILY_API = "https://api.weatherbit.io/v2.0/forecast/daily?";
    private static final String URL_WEATHERBIT_CURRENT_API = "https://api.weatherbit.io/v2.0/current?";
    private static final String LANG = "pt";
    RestTemplate restTemplate = new RestTemplate();


    public WeatherForecastDto getWeatherForecastByCityNameForSixteenDays(String cityName) {
        String urlWithParameters = URL_WEATHERBIT_DAILY_API+"city="+this.formatCityNameToAddAsParameter(cityName)+",BR&lang="+LANG+"&key="+KEY_WEATHERBIT_API;

        WeatherForecastDto weatherForecastDto = restTemplate.getForObject(urlWithParameters, WeatherForecastDto.class);

        return weatherForecastDto;
    }

    public WeatherForecastDto getCurrentWeatherForecastByCityName(String cityName) {
        String urlWithParameters = URL_WEATHERBIT_CURRENT_API+"city="+this.formatCityNameToAddAsParameter(cityName)+",BR&lang="+LANG+"&key="+KEY_WEATHERBIT_API;

        WeatherForecastDto weatherForecastDto = restTemplate.getForObject(urlWithParameters, WeatherForecastDto.class);

        return weatherForecastDto;
    }

    private String formatCityNameToAddAsParameter(String cityName){
        cityName = this.removeAccents(cityName);
        return this.replaceSpacesToPlusCharacter(cityName);
    }

    private String removeAccents(String cityName){
        String cityNameNormalized = Normalizer.normalize(cityName, Normalizer.Form.NFD);
        return cityNameNormalized.replaceAll("\\p{M}", "");
    }

    private String replaceSpacesToPlusCharacter(String cityName){
        return cityName.replace(" ", "+");
    }

}
