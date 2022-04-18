package br.com.ubots.estagio.BotMessenger.controller;

import br.com.ubots.estagio.BotMessenger.exceptions.exception.ResponseMessageException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        assertEquals(challenge, challengeResponse);
    }

    @Test
    public void testVerifyInvalidToken() throws Exception {
        String token = "invalidToken";
        String challenge = "challenge";

        when(tokenService.verifyToken(token)).thenReturn(false);

        mvc.perform(get("/webhook?hub.verify_token="+token+"&hub.challenge="+challenge))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testReceiveMessageFromUserAnd() throws Exception {
        EventRequest eventRequest = this.createEventRequest();
        when(messageService.sendMessage(eventRequest.getSenderId(), eventRequest.getTextMessage())).thenReturn(200);

        mvc.perform(post("/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest))
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    result.getResponse().getContentAsString()
                            .contains(WebhookEventStatus.EVENT_RECEIVED.toString());
                })
                .andReturn();
    }

    private EventRequest createEventRequest(){
        EventMessage eventMessage = EventMessage.builder()
                .message(new Message("123", "Mensagem de teste"))
                .field("teste")
                .recipient(new Recipient("123"))
                .timestamp("timestamp")
                .value(new Value(new Sender("123")))
                .build();

        List<EventMessage> eventMessages = new ArrayList<>();
        eventMessages.add(eventMessage);

        Entry entry = Entry
                .builder()
                .id("1")
                .time("time")
                .messaging(eventMessages)
                .build();

        List<Entry> entries = new ArrayList<>();

        EventRequest eventRequest = EventRequest
                .builder()
                .entry(entries)
                .build();

        return eventRequest;
    }

    @Test
    public void testReceiveTheMessageButCannotSendTheReply() throws Exception {
        EventRequest eventRequest = this.createEventRequest();
        String errorMessage = "Não foi possível enviar uma resposta para o messenger!";
        when(messageService.sendMessage(eventRequest.getSenderId(), eventRequest.getTextMessage()))
                .thenThrow(new ResponseMessageException("Não foi possível enviar uma resposta para o messenger!"));

        mvc.perform(post("/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventRequest))
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResponseMessageException))
                .andExpect(result -> assertEquals(errorMessage, result.getResolvedException().getMessage()))
                .andExpect(result -> System.out.println(result.getResponse().getContentAsString()));

    }
}