package br.com.ubots.estagio.BotMessenger.controller;

import br.com.ubots.estagio.BotMessenger.model.*;
import br.com.ubots.estagio.BotMessenger.service.interfaces.MessageService;
import br.com.ubots.estagio.BotMessenger.service.interfaces.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private MessageService messageService;

    @Test
    public void testVerifyValidToken() throws Exception {
        String token = "123";
        String challenge = "challenge";

        when(tokenService.verifyToken(token)).thenReturn(true);

        mvc.perform(get("/webhook?hub.verify_token="+token+"&hub.challenge="+challenge))
                .andExpect(status().isOk());
    }

    @Test
    public void testVerifyChallenge() throws Exception {
        String token = "123";
        String challenge = "challengeCheck";
        when(tokenService.verifyToken(token)).thenReturn(true);

        MvcResult result = mvc.perform(get("/webhook?hub.verify_token="+token+"&hub.challenge="+challenge))
                .andReturn();
        String challengeResponse = result.getResponse().getContentAsString();

        Assertions.assertEquals(challenge, challengeResponse);
    }

    @Test
    public void testVerifyInvalidToken() throws Exception {
        String token = "invalidToken";
        String challenge = "challenge";

        when(tokenService.verifyToken(token)).thenReturn(false);

        mvc.perform(get("/webhook?hub.verify_token="+token+"&hub.challenge="+challenge))
                .andExpect(status().isUnauthorized());
    }

}