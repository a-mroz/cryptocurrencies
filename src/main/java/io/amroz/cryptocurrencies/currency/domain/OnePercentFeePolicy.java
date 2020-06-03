package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;

public class OnePercentFeePolicy implements FeePolicy {
    private static final BigDecimal ONE_PERCENT = new BigDecimal("0.01");

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(ONE_PERCENT);
    }
}
