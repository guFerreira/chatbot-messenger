package br.com.ubots.estagio.BotMessenger.service;

import org.springframework.http.ResponseEntity;

public interface IMensagemService {
    void enviarResposta(String id, String mensagemRecebida);
    ResponseEntity getWebHook(String token);
}
