package br.com.calculo.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public final class InputParser {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(new Locale("pt", "BR"));

    private InputParser() {
    }

    public static LocalDate parseDate(String value, String fieldName) {
        String normalized = requireNonBlank(value, fieldName);
        try {
            return LocalDate.parse(normalized, DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(fieldName + " inválida. Use o formato dd/MM/yyyy.", ex);
        }
    }

    public static BigDecimal parseMoney(String value, String fieldName) {
        String normalized = requireNonBlank(value, fieldName)
                .replace("R$", "")
                .replace(" ", "");

        if (normalized.contains(",")) {
            normalized = normalized.replace(".", "").replace(',', '.');
        }

        try {
            BigDecimal parsed = new BigDecimal(normalized);
            if (parsed.signum() <= 0) {
                throw new IllegalArgumentException(fieldName + " deve ser maior que zero.");
            }
            return parsed;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(fieldName + " inválido. Ex.: 8.500,00", ex);
        }
    }

    public static BigDecimal parsePercent(String value, String fieldName) {
        String normalized = requireNonBlank(value, fieldName)
                .replace("%", "")
                .replace(" ", "")
                .replace(',', '.');

        try {
            BigDecimal parsed = new BigDecimal(normalized);
            if (parsed.signum() < 0) {
                throw new IllegalArgumentException(fieldName + " não pode ser negativo.");
            }
            return parsed;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(fieldName + " inválido. Ex.: 15,00", ex);
        }
    }

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " é obrigatório.");
        }
        return value.trim();
    }
}
