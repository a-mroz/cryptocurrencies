package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;

class ZeroFeePolicy implements FeePolicy {

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
