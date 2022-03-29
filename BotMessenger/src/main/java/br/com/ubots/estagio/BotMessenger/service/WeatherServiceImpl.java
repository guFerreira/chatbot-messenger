package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.dto.WeatherForecastDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.Normalizer;
import java.text.Normalizer.Form;

//@Service
public class WeatherServiceImpl implements WeatherService{
    private static final String KEY_API_WEATHER = "104c57d8872e686e7b50663e6d63183d";
    private static final String URL_API_WEATHER_FORECAST = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    public WeatherForecastDTO getWeatherForecastByCityName(String cityName){
        RestTemplate restTemplate = new RestTemplate();
        String urlWithParameters = this.createUriWithParameters(cityName);

        WeatherForecastDTO weatherForecastDTO = restTemplate.getForObject(urlWithParameters, WeatherForecastDTO.class);

        return weatherForecastDTO;
    }

    private String createUriWithParameters(String cityName){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder
                .fromHttpUrl(URL_API_WEATHER_FORECAST)
                .queryParams(this.buildParametersToSearchByCity(cityName));
        return uriComponentsBuilder.toUriString();
    }

    private LinkedMultiValueMap<String, String> buildParametersToSearchByCity(String cityName){
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("q", this.formatCityNameToAddAsParameter(cityName));
        params.set("lang","pt_br");
        params.set("appid", KEY_API_WEATHER);

        return params;
    }

    private String formatCityNameToAddAsParameter(String cityName){
        cityName = this.removeAccents(cityName);
        return this.replaceSpacesToPlus(cityName);
    }

    private String removeAccents(String cityName){
        String cityNameNormalized = Normalizer.normalize(cityName, Form.NFD);
        return cityNameNormalized.replaceAll("\\p{M}", "");
    }

    private String replaceSpacesToPlus(String cityName){
        return cityName.replace(" ", "+");
    }

}
