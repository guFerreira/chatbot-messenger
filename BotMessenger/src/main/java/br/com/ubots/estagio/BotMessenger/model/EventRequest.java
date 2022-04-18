package br.com.ubots.estagio.BotMessenger.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public String getTextMessage(){
        if (!this.entry.isEmpty())
            return this.entry.get(0).getTextMessage();
        return null;

    }

    @JsonIgnore
    public String getSenderId(){
        if (!this.entry.isEmpty())
            return this.entry.get(0).getSenderId();
        return null;
    }


}
