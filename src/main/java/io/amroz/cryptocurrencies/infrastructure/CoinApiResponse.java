package io.amroz.cryptocurrencies.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

class CoinApiResponse {
    @JsonProperty("rate")
    BigDecimal rate;

    @JsonProperty("asset_id_base")
    String base;

    @JsonProperty("asset_id_quote")
    String quote;
}
