package br.com.ubots.estagio.BotMessenger.model.strategy;

import java.util.regex.Pattern;

public class Nome implements Response {
    @Override
    public String buildMessage() {
        return "O meu nome é Gustavo.";
    }

    @Override
    public boolean verifyIntents(String message) {
        String regexIntents = "^([\\w\\sÀ-úçÇ,]*)?([sS]eu nome|[Ss]e chama|[tT]e chamar|[lL]he chamar|[mM]e referir a você)[\\s,À-úçÇ,\\w\\?\\!\\.]*$";
        return Pattern.matches(regexIntents,message);
    }
}
