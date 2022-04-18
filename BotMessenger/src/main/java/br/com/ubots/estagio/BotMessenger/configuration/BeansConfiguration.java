package br.com.ubots.estagio.BotMessenger.configuration;

import br.com.ubots.estagio.BotMessenger.model.strategy.BuilderMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "br.com.ubots.estagio.BotMessenger.model.strategy")
public class BeansConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public BuilderMessage builderMessage() {
//        return new BuilderMessage();
//    }
}
