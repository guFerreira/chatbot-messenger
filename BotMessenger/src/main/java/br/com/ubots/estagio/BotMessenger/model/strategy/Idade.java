package br.com.ubots.estagio.BotMessenger.model.strategy;

public class Idade implements IResposta {
    @Override
    public String construirMensagem() {
        return "Eu tenho 21 anos!";
    }
}
