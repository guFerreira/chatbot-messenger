package br.com.ubots.estagio.BotMessenger.model.strategy;

public class Incompreendido implements Response {

    @Override
    public String buildMessage() {
        return "Eu não entendo muitas coisas ainda, tente perguntar sobre minha IDADE ou NOME :)";
    }

    @Override
    public boolean verifyIntents(String message) {
        return false;
    }
}
