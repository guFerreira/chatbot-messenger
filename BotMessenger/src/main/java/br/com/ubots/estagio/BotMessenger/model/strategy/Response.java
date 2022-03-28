package br.com.ubots.estagio.BotMessenger.model.strategy;

public interface Response {
    String buildMessage();
    boolean verifyIntents(String message);
}
