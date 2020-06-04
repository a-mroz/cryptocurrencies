package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;

public interface FeePolicy {

    /**
     * Calculates fee for a given amount.
     */
    Fee calculateFee(Cryptocurrency fromCurrency, BigDecimal amount);
}
