package br.com.ubots.estagio.BotMessenger.model.strategy;

public class Incompreendido implements IResponse {

    @Override
    public String buildMessage() {
        return "Eu não entendo muitas coisas ainda, tente perguntar sobre minha IDADE ou NOME :)";
    }
}
