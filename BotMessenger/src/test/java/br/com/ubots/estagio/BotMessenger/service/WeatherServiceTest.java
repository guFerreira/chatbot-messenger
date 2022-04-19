package br.com.ubots.estagio.BotMessenger.service;

import br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecastDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

    @Mock
    RestTemplate restTemplate;

    @Spy
    @InjectMocks
    WeatherServiceImpl weatherService;

    private String urlDaily;
    private String city;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(weatherService, "keyWeatherbitApi", "keyWeatherbit");
        ReflectionTestUtils.setField(weatherService, "urlWeatherbitDailyApi", "urlDailyTest/");
        ReflectionTestUtils.setField(weatherService, "urlWeatherbitCurrentApi", "urlCurrentTest/");
        ReflectionTestUtils.setField(weatherService, "lang", "pt");

        this.city = "Campinas";
        this.urlDaily = "urlDailyTest/city=" + city + ",BR&lang=pt&key=keyWeatherbit";
    }

    @Test
    public void testWeatherForecastByCityNameForDaysReturnsObjectByApi() {
        Mockito.when(restTemplate.getForEntity(urlDaily, WeatherForecastDto.class))
                .thenReturn(ResponseEntity.ok(WeatherForecastDto.builder().city(this.city).build()));

        WeatherForecastDto weatherForecastDto = weatherService.getWeatherForecastByCityNameForDays("Campinas");

        Assertions.assertEquals(city, weatherForecastDto.getCity());

    }

    @Test
    public void testErrorGetDataFromApiWeatherForecastByDays() {
        Mockito.when(restTemplate.getForEntity(urlDaily, WeatherForecastDto.class))
                .thenReturn(ResponseEntity.badRequest().build());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            weatherService.getWeatherForecastByCityNameForDays("Campinas");
        });

        String expectedMessage = "Ocorreu algum erro ao buscar os dados de previsão do tempo na API";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

    }

}
