package io.amroz.cryptocurrencies.currency.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;


class RatesResponse {
    @JsonProperty
    private final String source;
    @JsonProperty
    private final Map<String, BigDecimal> rates;

    RatesResponse(String source, Map<Cryptocurrency, BigDecimal> rates) {
        this.source = source;

        this.rates = rates.entrySet()
            .stream()
            .collect(Collectors.toMap(e -> e.getKey().symbol(), Map.Entry::getValue));

    }
}
