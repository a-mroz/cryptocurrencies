package io.amroz.cryptocurrencies.currency.domain;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.*;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;


class OnePercentFeePolicyTest {
    private static final Percentage TOLERANCE = Percentage.withPercentage(0.001);
    private static final Cryptocurrency BTC = Cryptocurrency.forSymbol("BTC");

    private final OnePercentFeePolicy policy = new OnePercentFeePolicy();

    @Test
    void shouldCalculateOnePercent() {
        assertThat(calculateFee(ZERO)).isCloseTo(ZERO, TOLERANCE);

        assertThat(calculateFee(ONE)).isCloseTo(valueOf(0.01), TOLERANCE);
        assertThat(calculateFee(TEN)).isCloseTo(valueOf(0.1), TOLERANCE);
        assertThat(calculateFee(valueOf(100))).isCloseTo(ONE, TOLERANCE);

    }

    private BigDecimal calculateFee(BigDecimal bigDecimal) {
        return policy.calculateFee(BTC, bigDecimal).amount();
    }
}
