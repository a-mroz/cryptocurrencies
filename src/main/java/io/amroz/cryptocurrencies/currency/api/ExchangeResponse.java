package io.amroz.cryptocurrencies.currency.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import io.amroz.cryptocurrencies.currency.domain.ExchangeRate;

import java.util.Map;
import java.util.stream.Collectors;

class ExchangeResponse {
    @JsonProperty
    private String from;

    @JsonProperty
    private Map<String, ExchangeRate> exchangeRates;

    ExchangeResponse(String from, Map<Cryptocurrency, ExchangeRate> exchangeRates) {
        this.from = from;

        this.exchangeRates = exchangeRates.entrySet()
            .stream()
            .collect(Collectors.toMap(e -> e.getKey().symbol(), Map.Entry::getValue));

    }
}
