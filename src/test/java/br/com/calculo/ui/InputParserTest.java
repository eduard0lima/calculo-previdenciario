package br.com.calculo.ui;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputParserTest {

    @Test
    void shouldParseDateInPtBrFormat() {
        LocalDate date = InputParser.parseDate("04/02/2026", "Data");

        assertEquals(LocalDate.of(2026, 2, 4), date);
    }

    @Test
    void shouldParseMoneyInCommaFormat() {
        BigDecimal value = InputParser.parseMoney("8.500,00", "Valor");

        assertEquals(new BigDecimal("8500.00"), value);
    }

    @Test
    void shouldParseMoneyInDotFormat() {
        BigDecimal value = InputParser.parseMoney("8500.00", "Valor");

        assertEquals(new BigDecimal("8500.00"), value);
    }

    @Test
    void shouldParsePercentWithComma() {
        BigDecimal value = InputParser.parsePercent("15,00", "Percentual");

        assertEquals(new BigDecimal("15.00"), value);
    }

    @Test
    void shouldRejectBlankDate() {
        assertThrows(IllegalArgumentException.class, () -> InputParser.parseDate(" ", "Data"));
    }
}
