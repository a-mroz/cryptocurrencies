package io.amroz.cryptocurrencies.infrastructure;

import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

class CoinApiService {

    private static final String API_BASE_URL = "https://rest-sandbox.coinapi.io/v1/";
    private static final String EXCHANGE_URL_PATTERN = "exchangerate/%s/%s";

    private final WebClient webClient;

    CoinApiService(String apiKey) {
        this.webClient = WebClient.builder()
            .baseUrl(API_BASE_URL)
            .defaultHeader("X-CoinAPI-Key", apiKey)
            .build();
    }

    Mono<CoinApiResponse> fetchSingle(Cryptocurrency from, Cryptocurrency to) {
        return webClient.get()
            .uri(uriBuilder ->
                uriBuilder.path(String.format(EXCHANGE_URL_PATTERN, from.symbol(), to.symbol()))
                    .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(CoinApiResponse.class);
    }

}
