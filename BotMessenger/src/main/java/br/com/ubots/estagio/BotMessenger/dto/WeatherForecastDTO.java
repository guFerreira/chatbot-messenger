package br.com.ubots.estagio.BotMessenger.dto;

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
public class WeatherForecastDTO {
    private String name;
    @JsonProperty("main")
    private TemperatureDTO temperature;
    @JsonProperty("weather")
    private List<WeatherDTO> weathers;

}
