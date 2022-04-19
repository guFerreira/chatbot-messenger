package br.com.ubots.estagio.BotMessenger.model.strategy;

import br.com.ubots.estagio.BotMessenger.service.interfaces.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WeatherStrategyTest {

    @Mock
    WeatherService weatherService;

    @InjectMocks
    WeatherStrategy weatherStrategy;

    @Test
    public void verifyTrueIntent(){
        String intent = "#Clima";

        boolean result = weatherStrategy.verifyIntents(intent);

        Assertions.assertTrue(result);
    }

    @Test
    public void verifyFalseIntent(){
        String intent = "FalseIntent";

        boolean result = weatherStrategy.verifyIntents(intent);

        Assertions.assertFalse(result);
    }

    
}
