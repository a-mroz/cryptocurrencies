package io.amroz.cryptocurrencies.currency.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CryptocurrencyTest {

    @Test
    void shouldNotAllowEmptyValue() {
        assertThrows(AssertionError.class, () -> Cryptocurrency.forSymbol(""));
    }

    @Test
    void shouldCreate() {
        Cryptocurrency usd = Cryptocurrency.forSymbol("usd");
        assertThat(usd.symbol()).isEqualTo(("USD"));
    }
}
