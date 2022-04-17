package br.com.ubots.estagio.BotMessenger.model.strategy;

import com.google.cloud.dialogflow.v2.QueryResult;

public interface MessageCreationStrategy {
    String buildMessage(QueryResult queryResult);
    boolean verifyIntents(String message);
}
