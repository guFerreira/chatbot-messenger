package br.com.ubots.estagio.BotMessenger.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class EventMessage {
    private String field;
    private Value value;
    private Recipient recipient;
    private String timestamp;
    private Message message;
    private Sender sender;

    public String getTextMessage(){
        return this.message.getText();
    }
    public String getSenderId(){
        return this.sender.getId();
    }
}
