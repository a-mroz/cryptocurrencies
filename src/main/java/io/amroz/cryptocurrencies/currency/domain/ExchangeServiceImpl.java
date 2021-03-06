package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExchangeServiceImpl implements ExchangeService {

    private final CurrencyRatesService ratesService;
    private final FeePolicy feePolicy;

    public ExchangeServiceImpl(CurrencyRatesService ratesService, FeePolicy feePolicy) {
        this.ratesService = ratesService;
        this.feePolicy = feePolicy;
    }

    @Override
    public Map<Cryptocurrency, ExchangeRate> exchange(Cryptocurrency from, List<Cryptocurrency> to, BigDecimal amount) {
        Map<Cryptocurrency, BigDecimal> rates = ratesService.ratesFor(from, to);

        return rates.entrySet().stream().collect(Collectors.toMap(
            Map.Entry::getKey, rate -> calculateExchangeRate(amount, from, rate.getValue())
        ));
    }

    private ExchangeRate calculateExchangeRate(BigDecimal amount, Cryptocurrency fromCurrency, BigDecimal rate) {
        Fee fee = feePolicy.calculateFee(fromCurrency, amount); // fee from original amount, as per description
        BigDecimal result = amount.subtract(fee.amount()).multiply(rate);

        return new ExchangeRate.Builder()
            .withAmount(amount)
            .withRate(rate)
            .withResult(result)
            .withFee(fee)
            .build();
    }
}
