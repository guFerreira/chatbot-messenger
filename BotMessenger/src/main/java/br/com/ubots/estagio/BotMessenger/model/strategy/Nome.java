package br.com.ubots.estagio.BotMessenger.model.strategy;

public class Nome implements IResponse {
    @Override
    public String buildMessage() {
        return "O meu nome é Gustavo.";
    }
}
