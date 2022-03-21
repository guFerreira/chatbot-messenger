package br.com.ubots.estagio.BotMessenger.model.strategy;

public class Nome implements IResposta {
    @Override
    public String construirMensagem() {
        return "O meu nome é Gustavo.";
    }
}
