package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.dto.PostMessageDTO;
import br.com.ubots.estagio.BotMessenger.dto.PostMessageResponseDTO;
import br.com.ubots.estagio.BotMessenger.model.*;
import br.com.ubots.estagio.BotMessenger.model.strategy.*;
import br.com.ubots.estagio.BotMessenger.service.interfaces.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    @Value("${VERIFY_TOKEN}")
    private String verifyToken;

    @Value("${FB_MSG_URL}")
    private String facebookUrlApi;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public int sendMessage(String senderId, String receivedMessage) {
        PostMessageResponseDTO messageResponse = PostMessageResponseDTO
                .builder()
                .messageType("message")
                .recipient(new Recipient(senderId))
                .message(new PostMessageDTO(this.buildResponse(receivedMessage, senderId)))
                .build();

        ResponseEntity responseEntity = restTemplate.postForEntity(facebookUrlApi + verifyToken, messageResponse, PostMessageResponseDTO.class);

//        if (responseEntity.getStatusCodeValue() != 200){
//
//        }
        return responseEntity.getStatusCodeValue();
    }

    private String buildResponse(String receivedMessage, String senderId){
        String message = receivedMessage.toLowerCase(Locale.ROOT);
        BuilderMessage builderMessage = new BuilderMessage(message, senderId);

        return builderMessage.buildMessage();
    }

}
