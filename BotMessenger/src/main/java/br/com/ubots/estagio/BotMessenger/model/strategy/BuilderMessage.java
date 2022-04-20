package br.com.ubots.estagio.BotMessenger.model.strategy;

import br.com.ubots.estagio.BotMessenger.exceptions.exception.ConsumeApiException;
import br.com.ubots.estagio.BotMessenger.service.interfaces.AgentService;
import com.google.cloud.dialogflow.v2.QueryResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuilderMessage {
    private List<MessageCreationStrategy> strategies;

    private final AgentService agentService;

    private final WeatherStrategy weatherStrategy;

    private final String messageProblemInDialogflow = "Me desculpe, estou com problemas internos na minha mente.\n" +
            "Tente se comunicar comigo mais tarde.";
    public BuilderMessage(AgentService agentService, WeatherStrategy weatherStrategy) {
        this.agentService = agentService;
        this.weatherStrategy = weatherStrategy;
        this.strategies = new ArrayList<MessageCreationStrategy>();
        this.addMessageCreationStrategiesToTheContext();
    }

    private void addMessageCreationStrategiesToTheContext(){
        this.strategies.add(this.weatherStrategy);
    }

    public String build( String senderId, String receivedMessage){
        try {
            return this.buildMessage(senderId, receivedMessage);
        }catch (ConsumeApiException consumeApiException){
            return this.messageProblemInDialogflow;
        }

    }

    private String buildMessage(String senderId, String receivedMessage){
        QueryResult queryResult = agentService.detectIntentTexts(receivedMessage, senderId);

        if (queryResult == null){
            return this.messageProblemInDialogflow;
        }

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
