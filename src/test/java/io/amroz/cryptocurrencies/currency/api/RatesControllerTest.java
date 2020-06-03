package io.amroz.cryptocurrencies.currency.api;

import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import io.amroz.cryptocurrencies.currency.domain.CurrencyRatesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.amroz.cryptocurrencies.currency.domain.Cryptocurrency.forSymbol;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RatesControllerTest {

    @Mock
    private CurrencyRatesService ratesService;

    private RatesController ratesController;

    @BeforeEach
    void setUp() {
        ratesController = new RatesController(ratesService);
    }

    @Test
    void shouldRemoveCurrentCurrencyFromFilters() {
        // when
        ratesController.getRates("BTC", Arrays.asList("BTC", "ETH"));

        // then
        verify(ratesService).ratesFor(forSymbol("BTC"), List.of(forSymbol("ETH")));
    }

    @Test
    void shouldUseDefaultForEmptyFilters() {
        // given
        List<Cryptocurrency> defaultFilter = List.of(forSymbol("USD"), forSymbol("BTC"), forSymbol("ETH"));
        String symbolFrom = "ABC";

        // when
        ratesController.getRates(symbolFrom, Collections.emptyList());

        // then
        verify(ratesService).ratesFor(forSymbol(symbolFrom), defaultFilter);
    }
}
