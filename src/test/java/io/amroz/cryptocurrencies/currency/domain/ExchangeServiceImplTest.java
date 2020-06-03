package io.amroz.cryptocurrencies.currency.domain;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceImplTest {
    private static final Cryptocurrency BTC = Cryptocurrency.forSymbol("BTC");
    private static final Cryptocurrency ETH = Cryptocurrency.forSymbol("ETH");

    @Mock
    private CurrencyRatesService ratesService;

    @Test
    void shouldCalculateWithoutFee() {
        // given
        BigDecimal amount = BigDecimal.ONE;
        BigDecimal rate = BigDecimal.TEN;

        when(ratesService.ratesFor(BTC, List.of(ETH))).thenReturn(Map.of(ETH, rate));

        ExchangeServiceImpl exchangeService = new ExchangeServiceImpl(ratesService, new ZeroFeePolicy());

        // when
        Map<Cryptocurrency, ExchangeRate> rates = exchangeService.exchange(BTC, List.of(ETH), amount);

        // then
        assertThat(rates).hasSize(1);
        ExchangeRate exchangeRate = rates.get(ETH);
        assertThat(exchangeRate).isNotNull();
        assertThat(exchangeRate.getAmount()).isEqualTo(amount);
        assertThat(exchangeRate.getFee()).isEqualTo(BigDecimal.ZERO);
        assertThat(exchangeRate.getRate()).isEqualTo(rate);
        assertThat(exchangeRate.getResult()).isEqualTo(amount.multiply(rate));
    }

    @Test
    void shouldSkipIfNoRateFound() {
        // given
        ExchangeServiceImpl exchangeService = new ExchangeServiceImpl(ratesService, new ZeroFeePolicy());
        when(ratesService.ratesFor(any(Cryptocurrency.class), ArgumentMatchers.anyList())).thenReturn(Map.of());

        // when
        Map<Cryptocurrency, ExchangeRate> rates = exchangeService.exchange(BTC, List.of(ETH), BigDecimal.TEN);

        // then
        assertThat(rates).isEmpty();
    }

    @Test
    void shouldSubtractFee() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100);
        BigDecimal rate = BigDecimal.ONE;

        ExchangeServiceImpl exchangeService = new ExchangeServiceImpl(ratesService, new OnePercentFeePolicy());
        when(ratesService.ratesFor(BTC, List.of(ETH))).thenReturn(Map.of(ETH, rate));

        // when
        Map<Cryptocurrency, ExchangeRate> rates = exchangeService.exchange(BTC, List.of(ETH), amount);

        // then
        Percentage accuracy = Percentage.withPercentage(0.0001);

        ExchangeRate exchangeRate = rates.get(ETH);

        BigDecimal expectedFee = BigDecimal.ONE; // one percent of 100
        BigDecimal expectedResult = BigDecimal.valueOf(99); // 1 to 1 rate - fee

        assertThat(exchangeRate.getFee()).isCloseTo(expectedFee, accuracy);
        assertThat(exchangeRate.getResult()).isCloseTo(expectedResult, accuracy);
    }
}
