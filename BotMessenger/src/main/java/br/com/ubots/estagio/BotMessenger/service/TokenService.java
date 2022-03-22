package br.com.ubots.estagio.BotMessenger.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TokenService implements ITokenService{
    @Value("${VERIFY_TOKEN}")
    private String verifyToken;

    public boolean verifyToken(String receivedToken) {
        if(receivedToken != null && !receivedToken.isEmpty() && receivedToken.equals(verifyToken)){
            return true;
        }else{
            return false;
        }
    }

}
