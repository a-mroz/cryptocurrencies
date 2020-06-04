package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;

public class Fee {
    private BigDecimal amount;
    private Cryptocurrency currency;

    private Fee(BigDecimal amount, Cryptocurrency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    static Fee fee(Cryptocurrency currency, BigDecimal amount) {
        return new Fee(amount, currency);
    }

    public BigDecimal amount() {
        return amount;
    }

    public Cryptocurrency currency() {
        return currency;
    }
}
