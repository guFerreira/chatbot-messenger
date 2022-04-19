package br.com.ubots.estagio.BotMessenger.service.interfaces;

import br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecast;
import br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecastDto;

public interface WeatherService {
    WeatherForecastDto getWeatherForecastByCityNameForDays(String cityName);
    WeatherForecast getCurrentWeatherForecastByCityName(String cityName);

}
