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

    public String getTextMessage(){
        return this.messaging.get(0).getTextMessage();
    }
    public String getSenderId(){
        return this.messaging.get(0).getSenderId();
    }
}
