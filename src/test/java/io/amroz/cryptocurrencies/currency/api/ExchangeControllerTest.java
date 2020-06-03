package io.amroz.cryptocurrencies.currency.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.amroz.cryptocurrencies.currency.domain.Cryptocurrency;
import io.amroz.cryptocurrencies.currency.domain.ExchangeService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeController.class)
class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeService exchangeServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldRespondWithJson() throws Exception {
        when(exchangeServiceMock.exchange(any(Cryptocurrency.class), ArgumentMatchers.anyList(), any(BigDecimal.class)))
            .thenReturn(Collections.emptyMap());

        String body = objectMapper.writer().writeValueAsString(new ExchangeRequest("BTC", List.of("ETH"), BigDecimal.TEN));

        performRequest(body)
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("{\"from\":\"BTC\",\"exchangeRates\":{}}"));
    }

    @Test // similar for other cases
    void shouldRespondWith400ForEmptyCurrency() throws Exception {
        String body = objectMapper.writer().writeValueAsString(new ExchangeRequest("", List.of("ETH"),
            BigDecimal.TEN));

        performRequest(body)
            .andExpect(status().isBadRequest());
    }

    private ResultActions performRequest(String body) throws Exception {
        return this.mockMvc.perform(post("/currencies/exchange")
            .content(body)
            .contentType("application/json")
        );
    }
}
