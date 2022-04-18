package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.dto.PostMessageDTO;
import br.com.ubots.estagio.BotMessenger.dto.PostMessageResponseDTO;
import br.com.ubots.estagio.BotMessenger.exceptions.exception.ResponseMessageException;
import br.com.ubots.estagio.BotMessenger.model.Recipient;
import br.com.ubots.estagio.BotMessenger.model.strategy.BuilderMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    BuilderMessage builderMessage;

    @Spy
    @InjectMocks
    MessageServiceImpl messageService;

    private String verifyToken = "token";
    private String facebookUrlApi = "https://graph.facebook.com/v13.0/me/messages?access_token=";

    private PostMessageResponseDTO messageResponse;
    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(messageService, "facebookUrlApi", this.facebookUrlApi);
        ReflectionTestUtils.setField(messageService, "verifyToken", this.verifyToken);

        messageResponse = PostMessageResponseDTO
                .builder()
                .messageType("message")
                .recipient(new Recipient("12345"))
                .message(new PostMessageDTO("teste"))
                .build();

        Mockito.when(builderMessage
                        .build("12345", "mensagem de teste"))
                .thenReturn("teste");
    }

    @Test
    public void testSendingMessageSuccess(){
        Mockito.when(restTemplate.postForEntity(this.facebookUrlApi + this.verifyToken, messageResponse, PostMessageResponseDTO.class))
          .thenReturn(new ResponseEntity(messageResponse, HttpStatus.OK));

        int resultStatus = messageService.sendMessage("12345","mensagem de teste");

        Assertions.assertEquals(200, resultStatus);
    }


    @Test
    public void testSendingMessageFail(){
        Mockito.when(restTemplate.postForEntity(this.facebookUrlApi + this.verifyToken, messageResponse, PostMessageResponseDTO.class))
                .thenReturn(new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST));

        Exception exception = assertThrows(ResponseMessageException.class, () -> {
            messageService.sendMessage("12345","mensagem de teste");
        });

        String expectedMessage = "A resposta enviada ao messenger não foi aceita com sucesso";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

}
