package io.amroz.cryptocurrencies.currency.config;

import io.amroz.cryptocurrencies.currency.domain.CurrencyRatesService;
import io.amroz.cryptocurrencies.currency.domain.CurrencyRatesServiceImpl;
import io.amroz.cryptocurrencies.currency.domain.ExchangeService;
import io.amroz.cryptocurrencies.currency.domain.ExchangeServiceImpl;
import io.amroz.cryptocurrencies.currency.domain.SingleCurrencyRateFetcher;
import io.amroz.cryptocurrencies.infrastructure.CoinApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class CurrencyConfiguration {

    @Bean
    CurrencyRatesService currencyRatesService(SingleCurrencyRateFetcher singleCurrencyRateFetcher) {
        return new CurrencyRatesServiceImpl(singleCurrencyRateFetcher, Executors.newCachedThreadPool());
    }

    @Bean
    SingleCurrencyRateFetcher singleCurrencyRateFetcher(@Value("${coinapi.api-key}") String apiKey) {
        return new CoinApiService(apiKey);
    }

    @Bean
    ExchangeService exchangeService(CurrencyRatesService currencyRatesService) {
        return new ExchangeServiceImpl(currencyRatesService);
    }
}
