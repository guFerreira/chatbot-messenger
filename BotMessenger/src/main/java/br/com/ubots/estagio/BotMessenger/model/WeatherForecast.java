package br.com.ubots.estagio.BotMessenger.model;

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
public class WeatherForecast {
    @JsonProperty("name")
    private String cityName;
    @JsonProperty("main")
    private Temperature temperature;
    @JsonProperty("weather")
    private List<Weather> weathers;

    public String getWeatherDescription(){
        return this.weathers.get(0).getDescription();
    }
    public float getTemperatureInKelvin(){
        return this.temperature.getMeasureInKelvin();
    }

}
