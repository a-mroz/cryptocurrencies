package io.amroz.cryptocurrencies.currency.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import io.amroz.cryptocurrencies.currency.domain.ExchangeRate;
import io.amroz.cryptocurrencies.currency.domain.Fee;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

class ExchangeResponse {
    @JsonProperty
    private String from;

    @JsonProperty
    private Map<String, ExchangeRateResponse> exchangeRates;

    ExchangeResponse(String from, Map<Cryptocurrency, ExchangeRate> exchangeRates) {
        this.from = from;

        this.exchangeRates = exchangeRates.entrySet()
            .stream()
            .collect(Collectors.toMap(e -> e.getKey().symbol(),
                e -> ExchangeRateResponse.fromExchangeRate(e.getValue())));
    }

    private static class ExchangeRateResponse {
        @JsonProperty
        private BigDecimal rate;

        @JsonProperty
        private BigDecimal amount;

        @JsonProperty
        private BigDecimal result;

        @JsonProperty
        private FeeResponse fee;

        private static ExchangeRateResponse fromExchangeRate(ExchangeRate exchangeRate) {
            ExchangeRateResponse response = new ExchangeRateResponse();
            response.rate = exchangeRate.getRate();
            response.amount = exchangeRate.getAmount();
            response.result = exchangeRate.getResult();
            response.fee = new FeeResponse(exchangeRate.getFee());
            return response;
        }
    }

    private static class FeeResponse {
        @JsonProperty
        private String currency;

        @JsonProperty
        private BigDecimal amount;

        private FeeResponse(Fee fee) {
            this.currency = fee.currency().symbol();
            this.amount = fee.amount();
        }
    }

}
