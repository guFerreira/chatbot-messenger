package br.com.ubots.estagio.BotMessenger.service;

import java.io.IOException;

import br.com.ubots.estagio.BotMessenger.exceptions.exception.ConsumeApiException;
import br.com.ubots.estagio.BotMessenger.service.interfaces.AgentService;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AgentServiceImpl implements AgentService {
    @Value("${LANGUAGE_CODE}")
    private String languageCode;
    @Value("${DIALOGFLOW_PROJECT_ID}")
    private String projectId;

    public QueryResult detectIntentTexts(String text, String sessionId) {
        try (SessionsClient sessionsClient = SessionsClient.create()) {
            SessionName session = SessionName.of(projectId, sessionId);

            DetectIntentResponse response = sessionsClient
                    .detectIntent(session, this.buildQueryInput(text));

            return response.getQueryResult();

        } catch (IOException e) {
            throw new ConsumeApiException(e.getMessage());
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
