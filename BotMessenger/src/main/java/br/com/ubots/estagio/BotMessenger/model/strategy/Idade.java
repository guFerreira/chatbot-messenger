package br.com.ubots.estagio.BotMessenger.model.strategy;

public class Idade implements IResponse {
    @Override
    public String buildMessage() {
        return "Eu tenho 21 anos!";
    }
}
