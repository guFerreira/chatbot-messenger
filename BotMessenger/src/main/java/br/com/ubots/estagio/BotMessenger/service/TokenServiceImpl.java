package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.service.interfaces.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${VERIFY_TOKEN}")
    private String verifyToken;

    public boolean verifyToken(String receivedToken) {
        return receivedToken != null && !receivedToken.isEmpty() && receivedToken.equals(verifyToken);
    }

}
