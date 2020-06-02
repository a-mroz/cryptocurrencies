package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CurrencyRatesService {
    Map<Cryptocurrency, BigDecimal> ratesFor(Cryptocurrency currency, List<Cryptocurrency> toCurrencies);
}
