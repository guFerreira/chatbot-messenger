package br.com.ubots.estagio.BotMessenger.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class MensagemController {

    private final String MY_VERIFY_TOKEN = "escrever token aqui";

    @GetMapping("/webhook")
    public ResponseEntity getWebHook(@RequestParam(name = "hub.verify_token")String token,
                                     @RequestParam(name = "hub.challenge")String challenge){
        if(token!=null && !token.isEmpty() && token.equals(MY_VERIFY_TOKEN)){
            return ResponseEntity.ok(challenge);
        }else{
            return ResponseEntity.badRequest().body("Wrong Token");
        }
    }
    @GetMapping("/teste")
    public ResponseEntity testeWebHook(){
        return ResponseEntity.ok("TESTE CHATBOT");
    }

    @PostMapping("/webhook")
    public ResponseEntity postWebHook(@RequestBody String s){

        return ResponseEntity.ok("TESTE CHATBOT");
    }
}
