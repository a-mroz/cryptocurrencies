package io.amroz.cryptocurrencies.currency.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

class ExchangeRequest {
    @NotEmpty
    @JsonProperty
    private String from;

    @NotEmpty
    @JsonProperty
    private List<String> to;

    @Positive
    @NotNull
    @JsonProperty
    private BigDecimal amount;

    String from() {
        return from;
    }

    List<String> to() {
        return to;
    }

    BigDecimal amount() {
        return amount;
    }
}
