package br.com.ubots.estagio.BotMessenger.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Message {
    private String mid;
    private String text;
}
