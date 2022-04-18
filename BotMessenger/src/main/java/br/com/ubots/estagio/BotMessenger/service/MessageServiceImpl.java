package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.dto.PostMessageDTO;
import br.com.ubots.estagio.BotMessenger.dto.PostMessageResponseDTO;
import br.com.ubots.estagio.BotMessenger.exceptions.exception.ResponseMessageException;
import br.com.ubots.estagio.BotMessenger.model.*;
import br.com.ubots.estagio.BotMessenger.model.strategy.*;
import br.com.ubots.estagio.BotMessenger.service.interfaces.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    @Value("${VERIFY_TOKEN}")
    private String verifyToken;

    @Value("${FB_MSG_URL}")
    private String facebookUrlApi;

    private final RestTemplate restTemplate;

    private final BuilderMessage builderMessage;


    @Override
    public int sendMessage(String senderId, String receivedMessage) {
        PostMessageResponseDTO messageResponse = PostMessageResponseDTO
                .builder()
                .messageType("message")
                .recipient(new Recipient(senderId))
                .message(new PostMessageDTO(this.buildResponse(receivedMessage, senderId)))
                .build();

        ResponseEntity responseEntity = restTemplate.postForEntity(facebookUrlApi + verifyToken, messageResponse, PostMessageResponseDTO.class);

        if (responseEntity.getStatusCodeValue() != 200){
            throw new ResponseMessageException("A resposta enviada ao messenger não foi aceita com sucesso");
        }
        return responseEntity.getStatusCodeValue();
    }

    private String buildResponse(String receivedMessage, String senderId){
        String message = receivedMessage.toLowerCase(Locale.ROOT);

        return this.builderMessage.build(message, senderId);
    }

}
