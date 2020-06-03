package io.amroz.cryptocurrencies.infrastructure;

import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import io.amroz.cryptocurrencies.currency.domain.CurrencyRatesService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class CoinApiParallelCurrencyFetcher implements CurrencyRatesService {

    private final CoinApiService coinApiService;

    public CoinApiParallelCurrencyFetcher(CoinApiService coinApiService) {
        this.coinApiService = coinApiService;
    }

    @Override
    public Map<Cryptocurrency, BigDecimal> ratesFor(Cryptocurrency from, List<Cryptocurrency> toCurrencies) {
        List<CoinApiResponse> responses = Flux.fromIterable(toCurrencies)
            .parallel()
            .runOn(Schedulers.elastic())
            .flatMap(t -> coinApiService.fetchSingle(from, t).onErrorResume(e -> Mono.empty()))
            .sequential()
            .collectList()
            .block();

        return responses.stream().collect(Collectors.toMap(r -> Cryptocurrency.forSymbol(r.quote), r -> r.rate));
    }

}
