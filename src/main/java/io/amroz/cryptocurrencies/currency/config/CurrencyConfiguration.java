package io.amroz.cryptocurrencies.currency.config;

import io.amroz.cryptocurrencies.currency.domain.CurrencyRatesService;
import io.amroz.cryptocurrencies.currency.domain.ExchangeService;
import io.amroz.cryptocurrencies.currency.domain.ExchangeServiceImpl;
import io.amroz.cryptocurrencies.currency.domain.FeePolicy;
import io.amroz.cryptocurrencies.currency.domain.OnePercentFeePolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyConfiguration {

    @Bean
    ExchangeService exchangeService(CurrencyRatesService currencyRatesService, FeePolicy feePolicy) {
        return new ExchangeServiceImpl(currencyRatesService, feePolicy);
    }

    @Bean
    FeePolicy feePolicy() {
        return new OnePercentFeePolicy();
    }
}
