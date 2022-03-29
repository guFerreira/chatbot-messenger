package br.com.ubots.estagio.BotMessenger.model.strategy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Idade implements Response {

    @Override
    public String buildMessage() {
        return "Eu tenho 21 anos!";
    }

    @Override
    public boolean verifyIntents(String message) {
        String regexIntents = "^[\\w\\sáàâãéèêíïóôõöúçñ]*\\s(idade|anos)[\\s\\w\\?\\!,áàâãéèêíïóôõöúçñ]*$";
        Pattern pattern = Pattern.compile(regexIntents);
        Matcher matcher = pattern.matcher(message);
        return matcher.find();
    }

}
