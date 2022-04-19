package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecast;
import br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecastDto;
import br.com.ubots.estagio.BotMessenger.service.interfaces.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;

@Service
@RequiredArgsConstructor
public class WeatherServiceWeatherbit implements WeatherService{
    @Value("${KEY_WEATHERBIT_API}")
    private String keyWeatherbitApi;
    @Value("${URL_WEATHERBIT_DAILY_API}")
    private String urlWeatherbitDailyApi;
    @Value("${URL_WEATHERBIT_CURRENT_API}")
    private String urlWeatherbitCurrentApi;
    @Value("${LANG_WEATHERBIT}")
    private String lang;

    private final RestTemplate restTemplate;


    @Override
    public WeatherForecastDto getWeatherForecastByCityNameForDays(String cityName) {
        String urlWithParameters = urlWeatherbitDailyApi +"city="+this.formatCityNameToAddAsParameter(cityName)+",BR&lang="+ lang +"&key="+ keyWeatherbitApi;
        System.out.println(lang);
        WeatherForecastDto weatherForecastDto = restTemplate.getForObject(urlWithParameters, WeatherForecastDto.class);

        return weatherForecastDto;
    }

    @Override
    public WeatherForecast getCurrentWeatherForecastByCityName(String cityName) {
        String urlWithParameters = urlWeatherbitCurrentApi +"city="+this.formatCityNameToAddAsParameter(cityName)+",BR&lang="+ lang +"&key="+ keyWeatherbitApi;

        WeatherForecastDto weatherForecastDto = restTemplate.getForObject(urlWithParameters, WeatherForecastDto.class);

        return weatherForecastDto.getWeatherForecast().get(0);
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
