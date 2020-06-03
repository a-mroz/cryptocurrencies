package io.amroz.cryptocurrencies.currency.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ExchangeRate {
    //TODO change
    @JsonProperty
    private BigDecimal rate;

    @JsonProperty
    private BigDecimal amount;

    @JsonProperty
    private BigDecimal result;

    @JsonProperty
    private BigDecimal fee;

    private ExchangeRate() {

    }

    static class Builder {
        private BigDecimal rate;
        private BigDecimal amount;
        private BigDecimal result;
        private BigDecimal fee;

        Builder withRate(BigDecimal rate) {
            this.rate = rate;
            return this;
        }

        Builder withAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        Builder withResult(BigDecimal result) {
            this.result = result;
            return this;
        }

        Builder withFee(BigDecimal fee) {
            this.fee = fee;
            return this;
        }

        ExchangeRate build() {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.amount = this.amount;
            exchangeRate.result = this.result;
            exchangeRate.fee = this.fee;
            exchangeRate.rate = this.rate;

            return exchangeRate;
        }
    }
}
