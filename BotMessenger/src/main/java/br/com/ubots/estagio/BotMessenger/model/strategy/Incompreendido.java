package br.com.ubots.estagio.BotMessenger.model.strategy;

public class Incompreendido implements Response {

    @Override
    public String buildMessage() {
        return "Desculpa, eu não entendi o que foi falado."+this.getEmojiThink()+"\n" +
                "Tente perguntar algo sobre sobre meu nome, minha idade ou" +
                " a previsão do tempo em uma cidade específica, " +
                "ficarei feliz em te ajudar! :D";
    }

    private String getEmojiThink(){
        return "\uD83E\uDD14";
    }
    @Override
    public boolean verifyIntents(String message) {
        return false;
    }
}
