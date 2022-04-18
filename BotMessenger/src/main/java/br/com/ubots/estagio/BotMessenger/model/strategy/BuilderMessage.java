package br.com.ubots.estagio.BotMessenger.model.strategy;

import br.com.ubots.estagio.BotMessenger.service.AgentService;
import com.google.cloud.dialogflow.v2.QueryResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuilderMessage {
    private List<MessageCreationStrategy> strategies;

    private final AgentService agentService;

    public BuilderMessage(AgentService agentService) {
        this.agentService = agentService;
        this.strategies = new ArrayList<MessageCreationStrategy>();
        this.addMessageCreationStrategiesToTheContext();
    }

    private void addMessageCreationStrategiesToTheContext(){
        this.strategies.add(new WeatherStrategy());
    }

    public String build(String receivedMessage, String senderId){
        QueryResult queryResult = agentService.detectIntentTexts(receivedMessage, senderId);

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
