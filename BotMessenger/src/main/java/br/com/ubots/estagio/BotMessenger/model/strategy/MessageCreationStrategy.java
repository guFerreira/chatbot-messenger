package br.com.ubots.estagio.BotMessenger.model.strategy;

public interface MessageCreationStrategy {
    String buildMessage();
    boolean verifyIntents(String message);
}
