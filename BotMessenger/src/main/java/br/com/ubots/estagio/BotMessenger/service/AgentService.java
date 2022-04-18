package br.com.ubots.estagio.BotMessenger.service;

import java.io.IOException;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;

public class AgentService {
    private String languageCode = "pt-BR";

    private String projectId = "chatbottest-ewkm";

    public QueryResult detectIntentTexts(String text, String sessionId) {
        try (SessionsClient sessionsClient = SessionsClient.create()) {
            SessionName session = SessionName.of(projectId, sessionId);

            DetectIntentResponse response = sessionsClient
                    .detectIntent(session, this.buildQueryInput(text));

            return response.getQueryResult();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TextInput buildTextInput(String text) {
        return TextInput.newBuilder().setText(text).setLanguageCode(languageCode).build();
    }

    private QueryInput buildQueryInput(String text) {
        return QueryInput.newBuilder()
                .setText(this.buildTextInput(text))
                .build();
    }
}
