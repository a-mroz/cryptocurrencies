package io.amroz.cryptocurrencies.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

class CoinApiAllCurrenciesResponse {

    @JsonProperty("asset_id_base")
    String assetIdBase;

    @JsonProperty("rates")
    List<CoinApiSingleRateResponse> rates;
}
