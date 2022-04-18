package br.com.ubots.estagio.BotMessenger.model.strategy;


import br.com.ubots.estagio.BotMessenger.service.AgentService;
import com.google.cloud.dialogflow.v2.Intent;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.QueryResultOrBuilder;
import com.google.protobuf.Struct;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BuilderMessageTest {
    @Mock
    private AgentService agentService;

    @InjectMocks
    private BuilderMessage builderMessage;

    @Test
    public void testBuildMessageByResponseFromDialogflow(){
        QueryResult queryResult = QueryResult
                .newBuilder()
                .setIntent(Intent.newBuilder().setDisplayName("nome").build())
                .setFulfillmentText("intencao que fala sobre o nome")
                .setAllRequiredParamsPresent(true)
                .build();
        Mockito.when(agentService.detectIntentTexts("text","sessionId"))
                .thenReturn(queryResult);

        String result = builderMessage.build("sessionId","text");

        Assertions.assertEquals(result, "intencao que fala sobre o nome");
    }
}
