package br.com.ubots.estagio.BotMessenger.model.weatherbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherForecast {
    @JsonProperty("weather")
    private Weather weather;
    @JsonProperty("min_temp")
    private float temperatureMin;
    @JsonProperty("max_temp")
    private float temperatureMax;
    @JsonProperty("temp")
    private float temp;
    @JsonProperty("datetime")
    private String date;


}
