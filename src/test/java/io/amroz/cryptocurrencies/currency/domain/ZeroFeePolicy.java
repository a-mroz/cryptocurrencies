package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;

class ZeroFeePolicy implements FeePolicy {

    @Override
    public Fee calculateFee(Cryptocurrency cryptocurrency, BigDecimal amount) {
        return Fee.fee(cryptocurrency, BigDecimal.ZERO);
    }
}
