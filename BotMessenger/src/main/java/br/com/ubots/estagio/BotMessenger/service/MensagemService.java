package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.model.*;
import br.com.ubots.estagio.BotMessenger.model.strategy.IResposta;
import br.com.ubots.estagio.BotMessenger.model.strategy.Idade;
import br.com.ubots.estagio.BotMessenger.model.strategy.Incompreendido;
import br.com.ubots.estagio.BotMessenger.model.strategy.Nome;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@Service
public class MensagemService implements IMensagemService{

    @Value("${VERIFY_TOKEN}")
    private String verifyToken;

    @Value("${FB_MSG_URL}")
    private String facebookUrlApi;

    @Override
    public ResponseEntity getWebHook(String receivedToken) {
        if(receivedToken!=null && !receivedToken.isEmpty() && receivedToken.equals(verifyToken)){
            return ResponseEntity.ok("Webhook Conectado");
        }else{
            return ResponseEntity.badRequest().body("Token incorreto");
        }
    }

    @Override
    public void enviarResposta(String id, String mensagemRecebida) {
        RestTemplate restTemplate = new RestTemplate();
        RequestMessageResposta rm = RequestMessageResposta
                .builder()
                .message_type("message")
                .recipient(new Recipient(id))
                .message(new RequestMessage(this.definirResposta(mensagemRecebida)))
                .build();

        restTemplate.postForEntity(facebookUrlApi + verifyToken, rm, RequestMessageResposta.class);
    }


    private String definirResposta(String mensagemRecebida){
        String mensagem = mensagemRecebida.toLowerCase(Locale.ROOT);
        IResposta resposta;

        if(mensagem.contains("idade") || mensagem.contains("anos")){
            resposta = new Idade();
        }else if(mensagem.contains("nome") || mensagem.contains("se chama")){
            resposta = new Nome();
        }else{
            resposta = new Incompreendido();
        }
        return resposta.construirMensagem();

    }

}
