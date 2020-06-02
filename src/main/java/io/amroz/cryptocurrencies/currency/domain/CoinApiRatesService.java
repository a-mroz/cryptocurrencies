package io.amroz.cryptocurrencies.currency.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinApiRatesService implements CurrencyRatesService {
    private static final String API_BASE_URL = "https://rest-sandbox.coinapi.io/v1/";
    private static final String EXCHANGE_URL_PATTERN = "exchangerate/%s/%s";

    private final WebClient webClient;

    public CoinApiRatesService(String apiKey) {
        this.webClient = WebClient.builder()
            .baseUrl(API_BASE_URL)
            .defaultHeader("X-CoinAPI-Key", apiKey)
            .build();
    }

    @Override
    public Map<Cryptocurrency, BigDecimal> ratesFor(Cryptocurrency currency, List<Cryptocurrency> toCurrencies) {

        Map<Cryptocurrency, BigDecimal> result = new HashMap<>();

        // TODO convert to threads; handle exceptions

        for (Cryptocurrency toCurrency: toCurrencies) {

            CoinApiResponse response = webClient.get()
            .uri(uriBuilder ->
                uriBuilder.path(String.format(EXCHANGE_URL_PATTERN, currency.symbol(), toCurrency.symbol()))
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(CoinApiResponse.class)
            .block();

            result.put(toCurrency, response.rate);
        }

        return result;
    }

    private static class CoinApiResponse {
        @JsonProperty("rate")
        private BigDecimal rate;
    }

}
