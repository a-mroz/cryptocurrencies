package io.amroz.cryptocurrencies.infrastructure;

import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoinApiParallelCurrencyFetcherTest {

    @Mock
    private CoinApiService coinApi;

    private CoinApiParallelCurrencyFetcher fetcher;

    @BeforeEach
    void setUp() {
        fetcher = new CoinApiParallelCurrencyFetcher(coinApi);
    }

    // Calling for unknown currencies causes exception, as it returns status 550
    @Test
    void shouldIgnoreExceptions() {
        // given
        when(coinApi.fetchSingle(any(Cryptocurrency.class), any(Cryptocurrency.class)))
            .thenReturn(Mono.error(mock(UnknownHttpStatusCodeException.class)));

        // when
        Map<Cryptocurrency, BigDecimal> rates = fetcher.ratesFor(Cryptocurrency.forSymbol("BTC"),
            List.of(Cryptocurrency.forSymbol("ETH")));

        // then
        assertThat(rates).isNotNull();
        assertThat(rates).isEmpty();
    }
}
