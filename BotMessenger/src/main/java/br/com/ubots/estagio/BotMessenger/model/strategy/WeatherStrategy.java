package br.com.ubots.estagio.BotMessenger.model.strategy;

import br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecast;
import br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecastDto;
import br.com.ubots.estagio.BotMessenger.service.WeatherServiceWeatherbit;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.protobuf.ListValue;
import com.google.protobuf.Struct;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@NoArgsConstructor
@Component
public class WeatherStrategy implements MessageCreationStrategy {
    private WeatherServiceWeatherbit weatherService = new WeatherServiceWeatherbit();

    @Override
    public boolean verifyIntents(String message) {
        return message.equals("#Clima");
    }

    @Override
    public String buildMessage(QueryResult queryResult) {
        String cityName = this.extractCityNameByParameters(queryResult);
        String date = this.extractDateByParameters(queryResult);

        return this.createWeatherForecastMessage(cityName, date);
    }

    private String extractCityNameByParameters(QueryResult queryResult) {
        Struct struct = queryResult.getParameters();
        ListValue listValue = struct.getFieldsMap().get("location").getListValue();
        String cityName = listValue.getValues(0).getStructValue()
                .getFieldsMap().get("city").getStringValue();

        return cityName;
    }

    private String extractDateByParameters(QueryResult queryResult) {
        Struct struct = queryResult.getParameters();
        String date = struct.getFieldsMap().get("date").getStringValue();
        String[] dates = date.split("T");
        return dates[0];
    }

    private String createWeatherForecastMessage(String cityName, String date) {
        if (this.isToday(date))
            return buildMessageForCurrentWeatherForecast(cityName);

        return buildWeatherForecastMessageBySpecificDate(cityName, date);
    }

    private boolean isToday(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        return date.equals(dtf.format(now));
    }

    private String buildMessageForCurrentWeatherForecast(String cityName) {
        WeatherForecast weatherForecast = this.weatherService.getCurrentWeatherForecastByCityName(cityName);
        String weatherDescription = weatherForecast.getWeather().getDescription();

        return "Na cidade " + cityName + ", o clima de agora é de: " +
                weatherDescription + ".\nA temperatura atual está em " +
                weatherForecast.getTemp() + "°C. " + this.getEmojiThermometer();
    }

    private String buildWeatherForecastMessageBySpecificDate(String cityName, String date) {
        WeatherForecastDto weatherForecastDto = this.weatherService.getWeatherForecastByCityNameForSixteenDays(cityName);
        Optional<WeatherForecast> weatherForecast = this.getWeatherForecastByDate(weatherForecastDto, date);

        if (weatherForecast.isPresent())
            return this.buildWeatherForecastMessageByDate(weatherForecast.get(), cityName);

        return this.fallbackNotFoundWeatherForecastByDate();
    }

    private Optional<WeatherForecast> getWeatherForecastByDate(WeatherForecastDto weatherForecastDto, String date) {
        Optional<WeatherForecast> weatherForecast = weatherForecastDto.getWeatherForecast().stream()
                .filter(wf -> wf.getDate().equals(date))
                .findFirst();
        return weatherForecast;
    }

    private String buildWeatherForecastMessageByDate(br.com.ubots.estagio.BotMessenger.model.weatherbit.WeatherForecast weatherForecast, String city) {
        String response = "Na cidade " + city + " " + this.getEmojiCity() +", o clima no dia " + weatherForecast.getDate()+ " é: " +
                weatherForecast.getWeather().getDescription() + ".\n A temperatura minima é de " +
                weatherForecast.getTemperatureMin() + "°C, e a máxima esperada é de " + weatherForecast.getTemperatureMax() + "°C. " + this.getEmojiThermometer();
        return response;
    }

    private String fallbackNotFoundWeatherForecastByDate(){
        return "Desculpe, não foi possível encontrar uma previsão do tempo pela data informada :(";
    }


    private String getEmojiThermometer() {
        return "\uD83C\uDF21";
    }

    private String getEmojiCity(){
        return "\uD83C\uDFD9";
    }

}
