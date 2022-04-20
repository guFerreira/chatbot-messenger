package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.exceptions.exception.ConsumeApiException;
import com.google.cloud.dialogflow.v2.SessionsClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class AgentServiceTest {

    @InjectMocks
    AgentServiceImpl agentService;

//    @Test
//    public void test() {
//        SessionsClient sessionsClient = mock(SessionsClient.class);
//        try {
//            Mockito.when(SessionsClient.create())
//                    .thenThrow(new ConsumeApiException(""));
//
//        }catch (IOException e){
//
//        }
//    }
}
