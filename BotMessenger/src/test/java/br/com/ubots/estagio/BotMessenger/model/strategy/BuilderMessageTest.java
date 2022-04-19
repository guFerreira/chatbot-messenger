package br.com.ubots.estagio.BotMessenger.model.strategy;


import br.com.ubots.estagio.BotMessenger.service.AgentServiceImpl;
import com.google.cloud.dialogflow.v2.Intent;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
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
    private AgentServiceImpl agentService;

    @Mock
    private  WeatherStrategy weatherStrategy;

    @InjectMocks
    private BuilderMessage builderMessage;

    @Test
    public void testBuildMessageByResponseFromDialogflow(){
        QueryResult queryResult = this.createQueryResultWithoutParameters();
        Mockito.when(agentService.detectIntentTexts("text","sessionId"))
                .thenReturn(queryResult);

        String result = builderMessage.build("sessionId","text");

        Assertions.assertEquals(result, "intencao que fala sobre o nome");
    }

    @Test
    public void testBuildMessageByResponseFromDialogflowWithParameters(){
        QueryResult queryResult = this.createQueryResultWithParameters();
        Mockito.when(agentService.detectIntentTexts("text","sessionId"))
                .thenReturn(queryResult);
        Mockito.when(weatherStrategy.verifyIntents("clima")).thenReturn(true);
        Mockito.when(weatherStrategy.buildMessage(queryResult))
                .thenReturn("mensagem personalizada sobre o clima");


        String result = builderMessage.build("sessionId","text");

        Assertions.assertEquals("mensagem personalizada sobre o clima", result);
    }

    @Test
    public void testBuildMessageByResponseFromDialogflowMissingMandatoryParameters(){
        QueryResult queryResult = this.createQueryResultWithMissingParameters();
        Mockito.when(agentService.detectIntentTexts("text","sessionId"))
                .thenReturn(queryResult);

        String result = builderMessage.build("sessionId","text");

        Assertions.assertEquals("falta parametros", result);
    }

    private QueryResult createQueryResultWithParameters(){
        Struct structCity = Struct
                .newBuilder()
                .putFields("city", Value
                        .newBuilder()
                        .setStringValue("Alegrete").
                        build())
                .build();

        Struct struct = Struct
                .newBuilder()
                .putFields("location", Value
                        .newBuilder()
                        .setStructValue(structCity)
                        .build())
                .build();

        return QueryResult
                .newBuilder()
                .setIntent(Intent.newBuilder().setDisplayName("clima").build())
                .setFulfillmentText("intencao sobre clima")
                .setAllRequiredParamsPresent(true)
                .setParameters(struct)
                .build();
    }

    private QueryResult createQueryResultWithoutParameters(){
        return QueryResult
                .newBuilder()
                .setIntent(Intent.newBuilder().setDisplayName("nome").build())
                .setFulfillmentText("intencao que fala sobre o nome")
                .setAllRequiredParamsPresent(true)
                .build();
    }

    private QueryResult createQueryResultWithMissingParameters(){
        return QueryResult
                .newBuilder()
                .setIntent(Intent.newBuilder().setDisplayName("clima").build())
                .setFulfillmentText("falta parametros")
                .setAllRequiredParamsPresent(false)
                .build();
    }
}
