package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.dto.PostMessageDTO;
import br.com.ubots.estagio.BotMessenger.dto.PostMessageResponseDTO;
import br.com.ubots.estagio.BotMessenger.model.*;
import br.com.ubots.estagio.BotMessenger.model.strategy.*;
import br.com.ubots.estagio.BotMessenger.service.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    @Value("${VERIFY_TOKEN}")
    private String verifyToken;

    @Value("${FB_MSG_URL}")
    private String facebookUrlApi;

    @Override
    public void sendMessage(String senderId, String receivedMessage) {
        RestTemplate restTemplate = new RestTemplate();
        PostMessageResponseDTO messageResponse = PostMessageResponseDTO
                .builder()
                .messageType("message")
                .recipient(new Recipient(senderId))
                .message(new PostMessageDTO(this.buildResponse(receivedMessage, senderId)))
                .build();

        restTemplate.postForEntity(facebookUrlApi + verifyToken, messageResponse, PostMessageResponseDTO.class);
    }

    private String buildResponse(String receivedMessage, String senderId){
        String message = receivedMessage.toLowerCase(Locale.ROOT);
        BuilderMessage builderMessage = new BuilderMessage(message, senderId);

        return builderMessage.buildMessage();
    }

}
