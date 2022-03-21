package br.com.ubots.estagio.BotMessenger.controller;

import br.com.ubots.estagio.BotMessenger.model.EventRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.ubots.estagio.BotMessenger.service.IMensagemService;

@RestController
public class MensagemController {

    private final IMensagemService mensagemService;

    public MensagemController(IMensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    @GetMapping("/webhook")
    public ResponseEntity getWebHook(@RequestParam(name = "hub.verify_token")String token){
        return this.mensagemService.getWebHook(token);
    }

    @PostMapping("/webhook")
    public ResponseEntity post(@RequestBody EventRequest request){
        String mensagemRecebida = request.getEntry().get(0)
                .getMessaging().get(0).getMessage().getText();

        String idSender = request.getEntry().get(0).getMessaging().get(0).getSender().getId();

        this.mensagemService.enviarResposta(idSender, mensagemRecebida);
        return ResponseEntity.status(HttpStatus.OK).body("EVENT_RECEIVED");
    }
}
