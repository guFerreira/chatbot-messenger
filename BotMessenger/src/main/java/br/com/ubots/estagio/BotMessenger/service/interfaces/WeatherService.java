package br.com.ubots.estagio.BotMessenger.service.interfaces;

import br.com.ubots.estagio.BotMessenger.model.WeatherForecast;

public interface WeatherService {
    WeatherForecast getWeatherForecastByCityName(String cityName);
}
