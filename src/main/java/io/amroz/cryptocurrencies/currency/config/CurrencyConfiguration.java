package io.amroz.cryptocurrencies.currency.config;

import io.amroz.cryptocurrencies.currency.domain.CoinApiRatesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyConfiguration {

    @Bean
    CoinApiRatesService currencyRatesService(@Value("${coinapi.api-key}") String apiKey) {
        return new CoinApiRatesService(apiKey);
    }
}
