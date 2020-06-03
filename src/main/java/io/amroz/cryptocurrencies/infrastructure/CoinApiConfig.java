package io.amroz.cryptocurrencies.infrastructure;

import io.amroz.cryptocurrencies.currency.domain.CurrencyRatesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CoinApiConfig {

    @Bean
    CurrencyRatesService currencyRatesService(@Value("${coinapi.api-key}") String apiKey) {
        return new CoinApiParallelCurrencyFetcher(new CoinApiService(apiKey));
    }
}
