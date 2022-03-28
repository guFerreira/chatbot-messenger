package br.com.ubots.estagio.BotMessenger.model.strategy;

public class Idade implements Response {

    @Override
    public String buildMessage() {
        return "Eu tenho 21 anos!";
    }

    @Override
    public boolean verifyIntents(String message) {
        return message.contains("idade") || message.contains("anos");
    }

}
