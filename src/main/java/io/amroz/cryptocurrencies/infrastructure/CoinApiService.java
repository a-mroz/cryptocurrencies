package io.amroz.cryptocurrencies.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import io.amroz.cryptocurrencies.currency.domain.CurrencyRatesService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CoinApiService implements CurrencyRatesService {

    private static final String API_BASE_URL = "https://rest-sandbox.coinapi.io/v1/";
    private static final String EXCHANGE_URL_PATTERN = "exchangerate/%s/%s";

    private final WebClient webClient;

    public CoinApiService(String apiKey) {
        this.webClient = WebClient.builder()
            .baseUrl(API_BASE_URL)
            .defaultHeader("X-CoinAPI-Key", apiKey)
            .build();
    }


    @Override
    public Map<Cryptocurrency, BigDecimal> ratesFor(Cryptocurrency from, List<Cryptocurrency> toCurrencies) {
        List<CoinApiResponse> responses = Flux.fromIterable(toCurrencies)
            .parallel()
            .runOn(Schedulers.elastic())
            .flatMap(t -> fetchSingle(from, t).onErrorResume(e -> Mono.empty()))
            .sequential()
            .collectList()
            .block();

        return responses.stream().collect(Collectors.toMap(r -> Cryptocurrency.forSymbol(r.quote), r -> r.rate));
    }

    private Mono<CoinApiResponse> fetchSingle(Cryptocurrency from, Cryptocurrency to) {
        return webClient.get()
            .uri(uriBuilder ->
                uriBuilder.path(String.format(EXCHANGE_URL_PATTERN, from.symbol(), to.symbol()))
                    .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(CoinApiResponse.class);
    }


    private static class CoinApiResponse {
        @JsonProperty("rate")
        private BigDecimal rate;

        @JsonProperty("asset_id_base")
        private String base;

        @JsonProperty("asset_id_quote")
        private String quote;
    }

}
