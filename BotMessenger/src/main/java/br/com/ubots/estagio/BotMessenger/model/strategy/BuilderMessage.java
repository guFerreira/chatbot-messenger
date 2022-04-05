package br.com.ubots.estagio.BotMessenger.model.strategy;

import java.util.ArrayList;
import java.util.List;

public class BuilderMessage {
    private String receivedMessage;
    private List<MessageCreationStrategy> strategies;
    private MessageCreationStrategy fallbackStrategy;

    public BuilderMessage(String receivedMessage) {
        this.receivedMessage = receivedMessage;
        this.strategies = new ArrayList<MessageCreationStrategy>();
        this.addMessageCreationStrategiesToTheContext();
    }

    private void addMessageCreationStrategiesToTheContext(){
        this.strategies.add(new AgeStrategy());
        this.strategies.add(new NameStrategy());
        this.strategies.add(new WeatherStrategy(this.receivedMessage));

        this.fallbackStrategy = new FallbackStrategy();
    }

    public String buildMessage(){
        for (MessageCreationStrategy strategy : this.strategies) {
            if(strategy.verifyIntents(this.receivedMessage)){
                return strategy.buildMessage();
            }
        }
        return this.fallbackStrategy.buildMessage();
    }

}
