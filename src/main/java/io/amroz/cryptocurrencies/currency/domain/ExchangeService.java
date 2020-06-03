package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ExchangeService {

    Map<Cryptocurrency, ExchangeRate> exchange(Cryptocurrency from, List<Cryptocurrency> to, BigDecimal amount);
}
