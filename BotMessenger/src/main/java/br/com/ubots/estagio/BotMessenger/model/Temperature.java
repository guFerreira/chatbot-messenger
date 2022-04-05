package br.com.ubots.estagio.BotMessenger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Temperature {
    @JsonProperty("temp")
    private float measureInKelvin;

}
