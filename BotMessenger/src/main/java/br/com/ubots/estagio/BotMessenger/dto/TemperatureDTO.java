package br.com.ubots.estagio.BotMessenger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureDTO {
    @JsonProperty("temp")
    private float measureInKelvin;
    @JsonProperty("feels_like")
    private float humanPerceptionOfWeather;
    private int humidity;

}
