package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.model.*;
import br.com.ubots.estagio.BotMessenger.model.strategy.*;
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

    WeatherService weatherService;


    public MessageServiceImpl(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public void sendMessage(String senderId, String receivedMessage) {
        RestTemplate restTemplate = new RestTemplate();
        PostMessageResponse messageResponse = PostMessageResponse
                .builder()
                .message_type("message")
                .recipient(new Recipient(senderId))
                .message(new PostMessage(this.buildResponse(receivedMessage)))
                .build();

        restTemplate.postForEntity(facebookUrlApi + verifyToken, messageResponse, PostMessageResponse.class);
    }


    private String buildResponse(String receivedMessage){
        String message = receivedMessage.toLowerCase(Locale.ROOT);
        BuilderMessage builderMessage = new BuilderMessage(message);

        return builderMessage.buildMessage();
    }

}
