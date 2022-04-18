package br.com.ubots.estagio.BotMessenger.model.strategy;

import br.com.ubots.estagio.BotMessenger.service.AgentService;
import com.google.cloud.dialogflow.v2.QueryResult;

import java.util.ArrayList;
import java.util.List;

public class BuilderMessage {
    private String receivedMessage;
    private String senderId;
    private List<MessageCreationStrategy> strategies;

    private AgentService agentService = new AgentService();
    public BuilderMessage(String receivedMessage, String senderId) {
        this.receivedMessage = receivedMessage;
        this.senderId = senderId;
        this.strategies = new ArrayList<MessageCreationStrategy>();
        this.addMessageCreationStrategiesToTheContext();
    }

    private void addMessageCreationStrategiesToTheContext(){
        this.strategies.add(new WeatherStrategy());
    }

    public String buildMessage(){
        QueryResult queryResult = agentService.detectIntentTexts(this.receivedMessage,this.senderId);

        if (!queryResult.getAllRequiredParamsPresent()){
            return queryResult.getFulfillmentText();
        }

        return this.buildMessageByStrategy(queryResult);

    }

    private String buildMessageByStrategy(QueryResult queryResult){
        for (MessageCreationStrategy strategy : this.strategies) {
            if(strategy.verifyIntents(this.getIntentName(queryResult))){
                return strategy.buildMessage(queryResult);
            }
        }
        return queryResult.getFulfillmentText();
    }

    private String getIntentName(QueryResult queryResult){
        return queryResult.getIntent().getDisplayName();
    }

}
