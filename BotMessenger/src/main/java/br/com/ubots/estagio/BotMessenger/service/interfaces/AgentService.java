package br.com.ubots.estagio.BotMessenger.service.interfaces;

import com.google.cloud.dialogflow.v2.QueryResult;

public interface AgentService {
    QueryResult detectIntentTexts(String text, String sessionId);
}
