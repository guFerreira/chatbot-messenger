package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.dto.PostMessageDTO;
import br.com.ubots.estagio.BotMessenger.dto.PostMessageResponseDTO;
import br.com.ubots.estagio.BotMessenger.exceptions.exception.InformationForReplyMessageException;
import br.com.ubots.estagio.BotMessenger.exceptions.exception.ResponseMessageException;
import br.com.ubots.estagio.BotMessenger.model.*;
import br.com.ubots.estagio.BotMessenger.model.messageBuildingStrategies.*;
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
        this.validateInformationsBySendMessage(senderId, receivedMessage);
        PostMessageResponseDTO messageResponse = this.createMessageResponse(senderId, receivedMessage);

        ResponseEntity responseEntity = restTemplate.postForEntity(facebookUrlApi + verifyToken, messageResponse, PostMessageResponseDTO.class);

        if (responseEntity.getStatusCodeValue() != 200){
            throw new ResponseMessageException("A resposta enviada ao messenger não foi aceita com sucesso");
        }
        return responseEntity.getStatusCodeValue();
    }

    private void validateInformationsBySendMessage(String senderId, String receivedMessage) {
        if (this.validateInformationForSendMessage(senderId)){
            throw new InformationForReplyMessageException("A informação senderId recebida é inválida");
        }
        if (this.validateInformationForSendMessage(receivedMessage)){
            throw new InformationForReplyMessageException("A informação receivedMessage recebida é inválida");
        }
    }

    private boolean validateInformationForSendMessage(String information){
        return information == null || information.equals("");
    }

    private PostMessageResponseDTO createMessageResponse(String senderId, String receivedMessage){
        return PostMessageResponseDTO
                .builder()
                .messageType("message")
                .recipient(new Recipient(senderId))
                .message(new PostMessageDTO(this.buildResponse(senderId, receivedMessage)))
                .build();
    }

    private String buildResponse(String senderId, String receivedMessage){
        String message = receivedMessage.toLowerCase(Locale.ROOT);
        return this.builderMessage.build(senderId, message);
    }

}
