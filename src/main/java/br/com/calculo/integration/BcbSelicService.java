package br.com.calculo.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BcbSelicService {

    private static final String DEFAULT_BASE_URL = "https://api.bcb.gov.br/dados/serie/bcdata.sgs.432/dados";
    private static final DateTimeFormatter BCB_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Pattern VALOR_PATTERN = Pattern.compile("\\\"valor\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"");

    private final HttpClient httpClient;
    private final String baseUrl;

    public BcbSelicService() {
        this(HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build(), DEFAULT_BASE_URL);
    }

    BcbSelicService(HttpClient httpClient, String baseUrl) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
    }

    public Optional<BigDecimal> fetchSelicPercent(LocalDate dataAcordo) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(buildUrl(dataAcordo)))
                    .timeout(Duration.ofSeconds(30))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return Optional.empty();
            }

            return extractFirstValor(response.body());
        } catch (IOException | InterruptedException | IllegalArgumentException ex) {
            if (ex instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return Optional.empty();
        }
    }

    String buildUrl(LocalDate dataAcordo) {
        String dataFormatada = dataAcordo.format(BCB_DATE_FORMAT);
        String dataEncodada = URLEncoder.encode(dataFormatada, StandardCharsets.UTF_8);
        return baseUrl + "?formato=json&dataInicial=" + dataEncodada + "&dataFinal=" + dataEncodada;
    }

    Optional<BigDecimal> extractFirstValor(String responseBody) {
        if (responseBody == null || responseBody.isBlank() || !responseBody.contains("\"valor\"")) {
            return Optional.empty();
        }

        Matcher matcher = VALOR_PATTERN.matcher(responseBody);
        if (!matcher.find()) {
            return Optional.empty();
        }

        String valor = matcher.group(1);
        try {
            return Optional.of(new BigDecimal(valor));
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
    }
}
