package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;

public class OnePercentFeePolicy implements FeePolicy {
    private static final BigDecimal ONE_PERCENT = new BigDecimal("0.01");

    @Override
    public Fee calculateFee(Cryptocurrency fromCurrency, BigDecimal amount) {
        return Fee.fee(fromCurrency, amount.multiply(ONE_PERCENT));
    }
}
