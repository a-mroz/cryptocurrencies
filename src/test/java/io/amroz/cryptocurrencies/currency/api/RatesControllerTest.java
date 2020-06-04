package io.amroz.cryptocurrencies.currency.api;

import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import io.amroz.cryptocurrencies.currency.domain.CurrencyRatesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void shouldFetchAllForEmptyFilters() {
        // given
        String symbolFrom = "BTC";

        // when
        ratesController.getRates(symbolFrom, Collections.emptyList());

        // then
        verify(ratesService).allRatesFor(forSymbol(symbolFrom));
    }

    @Test
    void shouldLimitForNonEmptyFilters() {
        // given
        String symbolFrom = "BTC";
        String filter = "ETH";

        // when
        ratesController.getRates(symbolFrom, List.of(filter));

        // then
        verify(ratesService).ratesFor(forSymbol(symbolFrom), List.of(Cryptocurrency.forSymbol(filter)));
    }
}
