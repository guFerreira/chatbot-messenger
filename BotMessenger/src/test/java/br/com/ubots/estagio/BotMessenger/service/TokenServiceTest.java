package br.com.ubots.estagio.BotMessenger.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    @Test
    public void testVerifyTrueToken(){
        String token = "tokenTest";
        ReflectionTestUtils.setField(tokenService, "verifyToken", "tokenTest");

        Boolean result = tokenService.verifyToken(token);

        Assertions.assertTrue(result);
    }

    @Test
    public void testVerifyFalseToken(){
        String token = "tokenFalse";
        ReflectionTestUtils.setField(tokenService, "verifyToken", "tokenTest");

        Boolean result = tokenService.verifyToken(token);

        Assertions.assertFalse(result);
    }

    @Test
    public void testVerifyNullToken(){
        String token = null;
        ReflectionTestUtils.setField(tokenService, "verifyToken", "tokenTest");

        Boolean result = tokenService.verifyToken(null);

        Assertions.assertFalse(result);
    }

    @Test
    public void testVerifyEmptyToken(){
        String token = "";
        ReflectionTestUtils.setField(tokenService, "verifyToken", "tokenTest");

        Boolean result = tokenService.verifyToken(token);

        Assertions.assertFalse(result);
    }
}
