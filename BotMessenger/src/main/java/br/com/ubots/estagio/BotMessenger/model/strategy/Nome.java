package br.com.ubots.estagio.BotMessenger.model.strategy;

public class Nome implements Response {
    @Override
    public String buildMessage() {
        return "O meu nome é Gustavo.";
    }

    @Override
    public boolean verifyIntents(String message) {
        return message.contains("nome") || message.contains("se chama");
    }
}
