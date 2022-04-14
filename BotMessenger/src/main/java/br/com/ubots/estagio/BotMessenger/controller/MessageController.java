package br.com.ubots.estagio.BotMessenger.controller;

import br.com.ubots.estagio.BotMessenger.model.EventRequest;
import br.com.ubots.estagio.BotMessenger.model.WebhookEventStatus;
import br.com.ubots.estagio.BotMessenger.service.interfaces.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.ubots.estagio.BotMessenger.service.interfaces.MessageService;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final TokenService tokenService;

    @GetMapping("/webhook")
    public ResponseEntity verifyWebHookToken(@RequestParam(name = "hub.verify_token")String token, @RequestParam(name = "hub.challenge")String challenge){
        if(this.tokenService.verifyToken(token)){
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(WebhookEventStatus.INVALID_TOKEN);
    }

    @PostMapping("/webhook")
    public ResponseEntity receiveMessageFromUser(@RequestBody EventRequest request){
        String receivedMessage = request.getTextMessage();
        String senderId = request.getSenderId();

        this.messageService.sendMessage(senderId, receivedMessage);
        return ResponseEntity.status(HttpStatus.OK).body(WebhookEventStatus.EVENT_RECEIVED);
    }
}
