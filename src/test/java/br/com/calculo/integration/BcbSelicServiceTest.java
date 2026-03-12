package br.com.calculo.integration;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BcbSelicServiceTest {

    private final BcbSelicService service = new BcbSelicService(HttpClient.newHttpClient(), "https://api.bcb.gov.br/dados/serie/bcdata.sgs.432/dados");

    @Test
    void shouldBuildUrlUsingDataAcordoAsInitialAndFinalDate() {
        String url = service.buildUrl(LocalDate.of(2026, 2, 4));

        assertEquals(
                "https://api.bcb.gov.br/dados/serie/bcdata.sgs.432/dados?formato=json&dataInicial=04%2F02%2F2026&dataFinal=04%2F02%2F2026",
                url
        );
    }

    @Test
    void shouldExtractValorFromJsonResponse() {
        Optional<BigDecimal> result = service.extractFirstValor("[{\"data\":\"04/02/2026\",\"valor\":\"15.00\"}]");

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("15.00"), result.get());
    }

    @Test
    void shouldReturnEmptyWhenValorIsMissing() {
        Optional<BigDecimal> result = service.extractFirstValor("[{\"data\":\"04/02/2026\"}]");

        assertTrue(result.isEmpty());
    }
}
