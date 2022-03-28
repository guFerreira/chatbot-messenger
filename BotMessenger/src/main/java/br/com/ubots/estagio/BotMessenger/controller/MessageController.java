package br.com.ubots.estagio.BotMessenger.controller;

import br.com.ubots.estagio.BotMessenger.model.EventRequest;
import br.com.ubots.estagio.BotMessenger.model.WebhookEventStatus;
import br.com.ubots.estagio.BotMessenger.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.ubots.estagio.BotMessenger.service.MessageService;

@RestController
public class MessageController {
    private final MessageService messageService;
    private final TokenService tokenService;

    public MessageController(MessageService messageService, TokenService tokenService) {
        this.messageService = messageService;
        this.tokenService = tokenService;
    }

    @GetMapping("/webhook")
    public ResponseEntity verifyWebHookToken(@RequestParam(name = "hub.verify_token")String token, @RequestParam(name = "hub.challenge")String challenge){
        if(this.tokenService.verifyToken(token)){
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(WebhookEventStatus.INVALID_TOKEN);
    }

    @PostMapping("/webhook")
    public ResponseEntity receiveMessageFromUser(@RequestBody EventRequest request){
        String receivedMessage = request.getEntry().get(0)
                .getMessaging().get(0).getMessage().getText();
        String senderId = request.getEntry().get(0).getMessaging().get(0).getSender().getId();

        this.messageService.sendMessage(senderId, receivedMessage);
        return ResponseEntity.status(HttpStatus.OK).body(WebhookEventStatus.EVENT_RECEIVED);
    }
}
