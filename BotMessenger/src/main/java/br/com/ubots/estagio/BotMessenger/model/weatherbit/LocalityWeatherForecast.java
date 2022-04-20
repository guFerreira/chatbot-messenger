package br.com.ubots.estagio.BotMessenger.model.weatherbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalityWeatherForecast {
    @JsonProperty("city_name")
    private String city;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("data")
    private List<WeatherForecast> weatherForecast;

}
