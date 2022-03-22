package br.com.ubots.estagio.BotMessenger.service;

public interface ITokenService {
    boolean verifyToken(String receivedToken);
}
