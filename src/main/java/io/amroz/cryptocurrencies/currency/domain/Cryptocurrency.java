package io.amroz.cryptocurrencies.currency.domain;

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
        if(symbol == null || symbol.isBlank()) { // potentially some util library
            throw new AssertionError("Symbol must be non-empty");
        }

        return new Cryptocurrency(symbol.toUpperCase());
    }

    public String symbol() {
        return symbol;
    }
}
