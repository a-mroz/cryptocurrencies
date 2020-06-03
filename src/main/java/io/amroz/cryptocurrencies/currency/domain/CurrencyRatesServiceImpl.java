package io.amroz.cryptocurrencies.currency.domain;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class CurrencyRatesServiceImpl implements CurrencyRatesService {
    private final SingleCurrencyRateFetcher singleCurrencyRateFetcher;
    private final ExecutorService executor;

    public CurrencyRatesServiceImpl(SingleCurrencyRateFetcher singleCurrencyRateFetcher, ExecutorService executorService) {
        this.singleCurrencyRateFetcher = singleCurrencyRateFetcher;
        this.executor = executorService;
    }

    @Override
    public Map<Cryptocurrency, BigDecimal> ratesFor(Cryptocurrency currency, List<Cryptocurrency> toCurrencies) {
        Map<Cryptocurrency, BigDecimal> result = new HashMap<>();

        // TODO handle exceptions

        Map<Cryptocurrency, CompletableFuture<BigDecimal>> collect = toCurrencies.stream()
            .collect(Collectors.toMap(toCurrency -> toCurrency,
                toCurrency -> CompletableFuture.supplyAsync(() -> singleCurrencyRateFetcher.fetchRate(currency, toCurrency), executor)));

        CompletableFuture.allOf(collect.values().toArray(new CompletableFuture[0])).join();

        return collect.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> {
            try {
                return e.getValue().get();
            } catch (InterruptedException | ExecutionException interruptedException) {
                interruptedException.printStackTrace();
            }
            return null;
        }));
    }



}
