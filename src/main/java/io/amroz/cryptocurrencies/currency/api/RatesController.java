package io.amroz.cryptocurrencies.currency.api;

import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import io.amroz.cryptocurrencies.currency.domain.CurrencyRatesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
class RatesController {

    private static final List<String> DEFAULT_CURRENCIES = List.of("USD", "BTC", "ETH");

    private final CurrencyRatesService ratesService;

    RatesController(CurrencyRatesService ratesService) {
        this.ratesService = ratesService;
    }

    @GetMapping("/currencies/{currency}")
    ResponseEntity<RatesResponse> getRates(@PathVariable("currency") @NotEmpty String currencySymbol,
                                           @RequestParam(value = "filter", required = false) List<String> currencyFilter) {
        Cryptocurrency currency = Cryptocurrency.forSymbol(currencySymbol);
        Map<Cryptocurrency, BigDecimal> rates = ratesService.ratesFor(currency, toCurrencies(currency, currencyFilter));

        RatesResponse body = new RatesResponse(currency.symbol(), rates);

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    private List<Cryptocurrency> toCurrencies(Cryptocurrency from, List<String> currencyFilter) {
        List<String> filter = (currencyFilter == null || currencyFilter.isEmpty()) ? new ArrayList<>(DEFAULT_CURRENCIES) : currencyFilter;

        // We don't want to convert from the same value to the same value
        filter.remove(from.symbol());

        return filter.stream().map(Cryptocurrency::forSymbol)
            .collect(Collectors.toList());
    }

}
