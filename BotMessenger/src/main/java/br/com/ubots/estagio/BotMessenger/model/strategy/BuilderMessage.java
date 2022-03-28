package br.com.ubots.estagio.BotMessenger.model.strategy;

import java.util.ArrayList;
import java.util.List;

public class BuilderMessage {
    private String receivedMessage;
    private List<Response> responses = new ArrayList<Response>();;
    private Response callback;

    public BuilderMessage(String receivedMessage) {
        this.responses.add(new Idade());
        this.responses.add(new Nome());
        this.responses.add(new Weather());

        this.callback = new Incompreendido();
        this.receivedMessage = receivedMessage;
    }

    public String buildMessage(){
        for (Response response: this.responses) {
            if(response.verifyIntents(this.receivedMessage)){
                return response.buildMessage();
            }
        }
        return this.callback.buildMessage();
    }

}
