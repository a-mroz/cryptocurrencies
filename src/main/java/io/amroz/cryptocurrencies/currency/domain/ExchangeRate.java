package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;

public class ExchangeRate {
    private BigDecimal rate;

    private BigDecimal amount;

    private BigDecimal result;

    private Fee fee;

    private ExchangeRate() {

    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getResult() {
        return result;
    }

    public Fee getFee() {
        return fee;
    }

    static class Builder {
        private BigDecimal rate;
        private BigDecimal amount;
        private BigDecimal result;
        private Fee fee;

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

        Builder withFee(Fee fee) {
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
