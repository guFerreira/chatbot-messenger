package br.com.ubots.estagio.BotMessenger.service.interfaces;

import br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecast;
import br.com.ubots.estagio.BotMessenger.model.weatherbit.LocalityWeatherForecast;

public interface WeatherService {
    LocalityWeatherForecast getWeatherForecastByCityNameForDays(String cityName);
    WeatherForecast getCurrentWeatherForecastByCityName(String cityName);

}
