package br.com.ubots.estagio.BotMessenger.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EventRequest {
    private String object = "page";
    private List<Entry> entry;
}
