package br.com.ubots.estagio.BotMessenger.controller;

import br.com.ubots.estagio.BotMessenger.model.EventRequest;
import br.com.ubots.estagio.BotMessenger.service.ITokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.ubots.estagio.BotMessenger.service.IMessageService;

@RestController
public class MessageController {
    private final IMessageService messageService;
    private final ITokenService tokenService;

    public MessageController(IMessageService messageService, ITokenService tokenService) {
        this.messageService = messageService;
        this.tokenService = tokenService;
    }

    @GetMapping("/webhook")
    public ResponseEntity verifyWebHookToken(@RequestParam(name = "hub.verify_token")String token, @RequestParam(name = "hub.challenge")String challenge){
        if(this.tokenService.verifyToken(token)){
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
    }

    @PostMapping("/webhook")
    public ResponseEntity post(@RequestBody EventRequest request){
        String receivedMessage = request.getEntry().get(0)
                .getMessaging().get(0).getMessage().getText();
        String idSender = request.getEntry().get(0).getMessaging().get(0).getSender().getId();

        this.messageService.sendResponse(idSender, receivedMessage);
        return ResponseEntity.status(HttpStatus.OK).body("EVENT_RECEIVED");
    }
}
