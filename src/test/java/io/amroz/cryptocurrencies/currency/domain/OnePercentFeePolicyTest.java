package io.amroz.cryptocurrencies.currency.domain;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


class OnePercentFeePolicyTest {
    private static final Percentage TOLERANCE = Percentage.withPercentage(0.001);

    private final OnePercentFeePolicy policy = new OnePercentFeePolicy();

    @Test
    void shouldCalculateOnePercent() {
        assertThat(policy.calculateFee(BigDecimal.ZERO)).isCloseTo(BigDecimal.ZERO, TOLERANCE);

        assertThat(policy.calculateFee(BigDecimal.ONE)).isCloseTo(BigDecimal.valueOf(0.01), TOLERANCE);
        assertThat(policy.calculateFee(BigDecimal.TEN)).isCloseTo(BigDecimal.valueOf(0.1), TOLERANCE);
        assertThat(policy.calculateFee(BigDecimal.valueOf(100))).isCloseTo(BigDecimal.ONE, TOLERANCE);

    }
}
