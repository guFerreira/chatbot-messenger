package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.dto.WeatherForecastDTO;

public interface WeatherService {
    WeatherForecastDTO getWeatherForecastByCityName(String cityName);
}
