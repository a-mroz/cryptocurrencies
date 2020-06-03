package io.amroz.cryptocurrencies.currency.api;

import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import io.amroz.cryptocurrencies.currency.domain.ExchangeRate;
import io.amroz.cryptocurrencies.currency.domain.ExchangeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
class ExchangeController {

    private final ExchangeService exchangeService;

    ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping("/currencies/exchange")
    ResponseEntity<ExchangeResponse> exchange(@RequestBody @Valid ExchangeRequest request) {
        Cryptocurrency from = Cryptocurrency.forSymbol(request.from());

        List<Cryptocurrency> toCurrencies = request.to()
            .stream()
            .map(Cryptocurrency::forSymbol)
            .collect(Collectors.toList());

        Map<Cryptocurrency, ExchangeRate> exchangeRates = exchangeService.exchange(from,
            toCurrencies, request.amount());

        ExchangeResponse response = new ExchangeResponse(from.symbol(), exchangeRates);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
