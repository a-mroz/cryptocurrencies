package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;

@FunctionalInterface
public interface SingleCurrencyRateFetcher {

    /**
     * Fetches currency rate between two values.
     * Must be thread-safe.
     * This might be a network call.
     */
    BigDecimal fetchRate(Cryptocurrency from, Cryptocurrency to);
}
