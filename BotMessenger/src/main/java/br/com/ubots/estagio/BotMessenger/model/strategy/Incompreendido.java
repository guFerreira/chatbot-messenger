package br.com.ubots.estagio.BotMessenger.model.strategy;

public class Incompreendido implements IResposta{

    @Override
    public String construirMensagem() {
        return "Eu não entendo muitas coisas ainda, tente perguntar sobre minha IDADE ou NOME :)";
    }
}
