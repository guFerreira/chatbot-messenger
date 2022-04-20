package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.exceptions.exception.ConsumeApiException;
import br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecast;
import br.com.ubots.estagio.BotMessenger.model.weatherbit.LocalityWeatherForecast;
import br.com.ubots.estagio.BotMessenger.service.interfaces.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.Normalizer;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
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
    public LocalityWeatherForecast getWeatherForecastByCityNameForDays(String cityName) {
        String urlWithParameters = urlWeatherbitDailyApi + this.createParametersForUrl(cityName);

        ResponseEntity<LocalityWeatherForecast> weatherForecastEntity = restTemplate
                .getForEntity(urlWithParameters, LocalityWeatherForecast.class);

        this.checkErrorGetDataFromApi(weatherForecastEntity.getStatusCodeValue());
        return weatherForecastEntity.getBody();
    }

    @Override
    public WeatherForecast getCurrentWeatherForecastByCityName(String cityName) {
        String urlWithParameters = urlWeatherbitCurrentApi + this.createParametersForUrl(cityName);

        ResponseEntity<LocalityWeatherForecast> weatherForecastEntity = restTemplate.getForEntity(urlWithParameters, LocalityWeatherForecast.class);

        this.checkErrorGetDataFromApi(weatherForecastEntity.getStatusCodeValue());
        return weatherForecastEntity.getBody().getWeatherForecast().get(0);
    }

    private String createParametersForUrl(String cityName) {
        return "city=" + this.formatCityNameToAddAsParameter(cityName) + ",BR&lang=" + lang + "&key=" + keyWeatherbitApi;
    }

    private void checkErrorGetDataFromApi(int httpStatus){
        if (httpStatus != 200){
            throw new ConsumeApiException("Ocorreu algum erro ao buscar os dados de previsão do tempo na API");
        }
    }
    private String formatCityNameToAddAsParameter(String cityName) {
        cityName = this.removeAccents(cityName);
        return this.replaceSpacesToPlusCharacter(cityName);
    }

    private String removeAccents(String cityName) {
        String cityNameNormalized = Normalizer.normalize(cityName, Normalizer.Form.NFD);
        return cityNameNormalized.replaceAll("\\p{M}", "");
    }

    private String replaceSpacesToPlusCharacter(String cityName) {
        return cityName.replace(" ", "+");
    }

}
