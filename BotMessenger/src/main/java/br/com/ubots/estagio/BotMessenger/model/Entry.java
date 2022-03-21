package br.com.ubots.estagio.BotMessenger.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Entry {
    private String id;
    private String time;
    private List<EventMessage> messaging;
}
