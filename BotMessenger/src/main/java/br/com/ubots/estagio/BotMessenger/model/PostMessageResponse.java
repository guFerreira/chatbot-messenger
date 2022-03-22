package br.com.ubots.estagio.BotMessenger.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostMessageResponse {
    private String message_type;
    private Recipient recipient;
    private PostMessage message;
}
