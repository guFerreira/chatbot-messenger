package br.com.ubots.estagio.BotMessenger.exceptions;

import br.com.ubots.estagio.BotMessenger.exceptions.exception.InformationForReplyMessageException;
import br.com.ubots.estagio.BotMessenger.exceptions.exception.ResponseMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResponseMessageException.class)
    public ResponseEntity<ErrorMessage> badArgumentsException (ResponseMessageException e, HttpServletRequest request){

        ErrorMessage error = this.buildErrorMessage(HttpStatus.BAD_REQUEST.value(),
                "Erro ao tentar enviar uma resposta",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InformationForReplyMessageException.class)
    public ResponseEntity<ErrorMessage> informationForReplyMessageException (InformationForReplyMessageException e, HttpServletRequest request){

        ErrorMessage error = this.buildErrorMessage(HttpStatus.BAD_REQUEST.value(),
                "Informações da mensagem recebida do usuário inválidas",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    private ErrorMessage buildErrorMessage(int httpStatus, String type, String message, String path) {
        return ErrorMessage.builder()
                .timestamp(Instant.now())
                .status(httpStatus)
                .type(type)
                .message(message)
                .path(path)
                .build();
    }

}
