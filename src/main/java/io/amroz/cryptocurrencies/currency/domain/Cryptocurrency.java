package io.amroz.cryptocurrencies.currency.domain;

import java.util.Objects;

/**
 * Value object representing currency.
 * Validates if symbol is not empty. It does *not* validate if given symbol is a valid currency symbol.
 *
 */
public class Cryptocurrency {
    private final String symbol;

    private Cryptocurrency(String symbol) {
        this.symbol = symbol;
    }

    public static Cryptocurrency forSymbol(String symbol) {
        if(symbol == null || symbol.isBlank()) {
            throw new AssertionError("Symbol must be non-empty");
        }

        return new Cryptocurrency(symbol.toUpperCase());
    }

    public String symbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cryptocurrency)) {
            return false;
        }
        Cryptocurrency that = (Cryptocurrency) o;
        return Objects.equals(symbol, that.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    @Override
    public String toString() {
        return symbol;
    }
}
