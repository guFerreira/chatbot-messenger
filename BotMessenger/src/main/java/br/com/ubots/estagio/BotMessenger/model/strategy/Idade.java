package br.com.ubots.estagio.BotMessenger.model.strategy;

import java.util.regex.Pattern;

public class Idade implements Response {

    @Override
    public String buildMessage() {
        return "Eu tenho 21 anos!";
    }

    @Override
    public boolean verifyIntents(String message) {
        String regexIntents = "^[\\w\\sÀ-úçÇ,]*\\s(idade|anos)[\\s\\w\\?\\!,À-úçÇ]*$";
        return Pattern.matches(regexIntents,message);
    }

}
