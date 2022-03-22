package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.model.*;
import br.com.ubots.estagio.BotMessenger.model.strategy.IResponse;
import br.com.ubots.estagio.BotMessenger.model.strategy.Idade;
import br.com.ubots.estagio.BotMessenger.model.strategy.Incompreendido;
import br.com.ubots.estagio.BotMessenger.model.strategy.Nome;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class MessageService implements IMessageService {

    @Value("${VERIFY_TOKEN}")
    private String verifyToken;

    @Value("${FB_MSG_URL}")
    private String facebookUrlApi;

    @Override
    public void sendResponse(String id, String receivedMessage) {
        RestTemplate restTemplate = new RestTemplate();
        PostMessageResponse rm = PostMessageResponse
                .builder()
                .message_type("message")
                .recipient(new Recipient(id))
                .message(new PostMessage(this.buildResponse(receivedMessage)))
                .build();

        restTemplate.postForEntity(facebookUrlApi + verifyToken, rm, PostMessageResponse.class);
    }


    private String buildResponse(String receivedMessage){
        String message = receivedMessage.toLowerCase(Locale.ROOT);
        IResponse responseMessage;

        if(message.contains("idade") || message.contains("anos")){
            responseMessage = new Idade();
        }else if(message.contains("nome") || message.contains("se chama")){
            responseMessage = new Nome();
        }else{
            responseMessage = new Incompreendido();
        }
        return responseMessage.buildMessage();
    }

}
