package br.com.ubots.estagio.BotMessenger.model.messageBuildingStrategies;

import br.com.ubots.estagio.BotMessenger.exceptions.exception.ConsumeApiException;
import br.com.ubots.estagio.BotMessenger.model.weatherbit.Weather;
import br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecast;
import br.com.ubots.estagio.BotMessenger.model.weatherbit.LocalityWeatherForecast;
import br.com.ubots.estagio.BotMessenger.service.interfaces.WeatherService;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.protobuf.ListValue;
import com.google.protobuf.Struct;
import com.google.protobuf.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WeatherStrategyTest {

    @Mock
    WeatherService weatherService;

    @InjectMocks
    WeatherStrategy weatherStrategy;

    private String responseErrorMessageToCallWeatherApi = "Ops! Aconteceu algum erro ao buscar os dados de previsão do tempo. \n" +
            "Peço desculpas, tente novamente mais tarde!";

    @Test
    public void testVerifyTrueIntent(){
        String intent = "#Clima";

        boolean result = weatherStrategy.verifyIntents(intent);

        Assertions.assertTrue(result);
    }

    @Test
    public void testVerifyFalseIntent(){
        String intent = "FalseIntent";

        boolean result = weatherStrategy.verifyIntents(intent);

        Assertions.assertFalse(result);
    }

    @Test
    public void testBuildMessageWeatherForecastForToday(){
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        QueryResult queryResult = this.createQueryResult("Campinas", date);
        WeatherForecast weatherForecast = WeatherForecast
                .builder()
                .temp(20.5f)
                .weather(Weather.builder().description("Descricao de teste").build())
                .build();
        Mockito.when(this.weatherService.getCurrentWeatherForecastByCityName("Campinas"))
                .thenReturn(weatherForecast);

        String result = this.weatherStrategy.buildMessage(queryResult);

        Assertions.assertEquals("Na cidade Campinas, o clima de agora é de: Descricao de teste.\n" +
                "A temperatura atual está em 20.5°C. \uD83C\uDF21", result);
    }

    @Test
    public void testBuildMessageWeatherForecastForFutureDate(){
        String tomorrowDate = new SimpleDateFormat("yyyy-MM-dd").format(this.getTomorrowDate());
        String dateExibitionForUser = new SimpleDateFormat("dd/MM/yyyy").format(this.getTomorrowDate());
        QueryResult queryResult = this.createQueryResult("Campinas", tomorrowDate);
        Mockito.when(this.weatherService.getWeatherForecastByCityNameForDays("Campinas"))
                .thenReturn(this.createWeatherForecastDto(tomorrowDate));

        String result = this.weatherStrategy.buildMessage(queryResult);

        Assertions.assertEquals("Na cidade Campinas \uD83C\uDFD9, o clima no dia "+dateExibitionForUser+" é: Descricao de teste.\n" +
                " A temperatura minima é de 19.07°C, e a máxima esperada é de 30.3°C. \uD83C\uDF21", result);
    }

    @Test
    public void testBuildMessageErrorToGetDataForFutureDate(){
        String tomorrowDate = new SimpleDateFormat("yyyy-MM-dd").format(this.getTomorrowDate());
        QueryResult queryResult = this.createQueryResult("Campinas", tomorrowDate);
        Mockito.when(this.weatherService.getWeatherForecastByCityNameForDays("Campinas"))
                .thenThrow(new ConsumeApiException("Ocorreu algum erro ao buscar os dados de previsão do tempo na API"));

        String actualMessage = this.weatherStrategy.buildMessage(queryResult);
        assertEquals(this.responseErrorMessageToCallWeatherApi, actualMessage);
    }

    @Test
    public void testBuildMessageErrorToGetDataForCurrentDate(){
        String actualDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        QueryResult queryResult = this.createQueryResult("Campinas", actualDate);
        Mockito.when(this.weatherService.getCurrentWeatherForecastByCityName("Campinas"))
                .thenThrow(new ConsumeApiException("Ocorreu algum erro ao buscar os dados de previsão do tempo na API"));

        String actualMessage = this.weatherStrategy.buildMessage(queryResult);

        assertEquals(this.responseErrorMessageToCallWeatherApi, actualMessage);
    }

    @Test
    public void testBuildMessageNotFoundWeatherForecastByDate(){
        String actualDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String tomorrowDate = new SimpleDateFormat("yyyy-MM-dd").format(this.getTomorrowDate());
        QueryResult queryResult = this.createQueryResult("Campinas", tomorrowDate);
        Mockito.when(this.weatherService.getWeatherForecastByCityNameForDays("Campinas"))
                .thenReturn(this.createWeatherForecastDto(actualDate));

        String actualMessage = this.weatherStrategy.buildMessage(queryResult);

        String expectMessage = "Desculpe, não foi possível encontrar uma previsão do tempo pela data informada :(";
        assertEquals(expectMessage, actualMessage);
    }

    private QueryResult createQueryResult(String cityName, String date){
        Struct parameters = Struct
                .newBuilder()
                .putFields("date", this.createValueDate(date))
                .putFields("location", this.createValueByCity(cityName))
                .build();

        QueryResult queryResult = QueryResult.
                newBuilder()
                .setParameters(parameters)
                .build();

        return queryResult;
    }

    private Value createValueDate(String date){
        Value valueDate = Value.newBuilder()
                .setStringValue(date).build();
        return valueDate;
    }

    private Value createValueByCity(String cityName){
        Value valueCity = Value
                .newBuilder()
                .setStringValue(cityName)
                .build();

        Struct structLocation = Struct
                .newBuilder()
                .putFields("city", valueCity)
                .build();

        Value internValueLocation = Value
                .newBuilder()
                .setStructValue(structLocation)
                .build();

        Value valueLocation = Value.newBuilder()
                .setListValue(ListValue
                        .newBuilder()
                        .addValues(internValueLocation)
                        .build())
                .build();
        return valueLocation;
    }

    private Date getTomorrowDate(){
        Date tomorowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tomorowDate);
        calendar.add(Calendar.DATE, 1);
        tomorowDate = calendar.getTime();
        return tomorowDate;
    }

    private LocalityWeatherForecast createWeatherForecastDto(String date){
        WeatherForecast weatherForecast = WeatherForecast
                .builder()
                .temperatureMax(30.3f).temperatureMin(19.07f).date(date)
                .weather(Weather.builder().description("Descricao de teste").build())
                .build();
        List<WeatherForecast> weatherForecasts = new ArrayList<>();
        weatherForecasts.add(weatherForecast);
        return LocalityWeatherForecast.builder().city("Campinas")
                .weatherForecast(weatherForecasts).build();
    }
}
