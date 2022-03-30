package br.com.ubots.estagio.BotMessenger.dto;

import br.com.ubots.estagio.BotMessenger.model.Recipient;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostMessageResponseDTO {
    @JsonProperty("message_type")
    private String messageType;
    private Recipient recipient;
    private PostMessageDTO message;
}
