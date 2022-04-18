package br.com.ubots.estagio.BotMessenger.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private Instant timestamp;
    private Integer status;
    private String type;
    private String message;
    private String path;
}
