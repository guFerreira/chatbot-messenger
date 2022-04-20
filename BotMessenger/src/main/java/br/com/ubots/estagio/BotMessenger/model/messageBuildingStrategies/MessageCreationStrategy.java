package br.com.ubots.estagio.BotMessenger.model.messageBuildingStrategies;

import com.google.cloud.dialogflow.v2.QueryResult;

public interface MessageCreationStrategy {
    String buildMessage(QueryResult queryResult);
    boolean verifyIntents(String intent);
}
