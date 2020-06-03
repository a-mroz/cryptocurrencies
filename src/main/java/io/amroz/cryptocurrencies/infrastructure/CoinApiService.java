package io.amroz.cryptocurrencies.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import io.amroz.cryptocurrencies.currency.domain.SingleCurrencyRateFetcher;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

public class CoinApiService implements SingleCurrencyRateFetcher {

    private static final String API_BASE_URL = "https://rest-sandbox.coinapi.io/v1/";
    private static final String EXCHANGE_URL_PATTERN = "exchangerate/%s/%s";

    private final WebClient webClient;

    public CoinApiService(String apiKey) {
        this.webClient = WebClient.builder()
            .baseUrl(API_BASE_URL)
            .defaultHeader("X-CoinAPI-Key", apiKey)
            .build();
    }


    public BigDecimal fetchRate(Cryptocurrency from, Cryptocurrency to) {
        CoinApiResponse response = webClient.get()
            .uri(uriBuilder ->
                uriBuilder.path(String.format(EXCHANGE_URL_PATTERN, from.symbol(), to.symbol()))
                    .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(CoinApiResponse.class)
            .block();

        return response.rate;
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
