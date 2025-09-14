package com.ftdevs.sortingapp.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class InputValidatorTest {

    private InputValidatorTest() { }

    @Test
    void parseIntegerTest() {
        String input = "1";
        assertEquals(1, InputValidator.tryParseInteger(input));
    }

    @Test
    void parseLongTest() {
        String input = "2";
        assertEquals(2, InputValidator.tryParseLong(input));
    }

    @Test
    void parseFloatTest() {
        String input = "3.33";
        assertEquals(3.33f, InputValidator.tryParseFloat(input));
    }

    @Test
    void parseDoubleTest() {
        String input = "4.44";
        assertEquals(4.44d, InputValidator.tryParseDouble(input));
    }

    @Test
    void nonValidIntegerTest() {
        String input = "qwerty";
        assertTrue(InputValidator.tryParseInteger(input) == null);
    }

    @Test
    void nonValidLongTest() {
        String input = "qwerty";
        assertTrue(InputValidator.tryParseLong(input) == null);
    }

    @Test
    void nonValidFloatTest() {
        String input = "qwerty";
        assertTrue(InputValidator.tryParseFloat(input) == null);
    }

    @Test
    void nonValidDoubleTest() {
        String input = "qwerty";
        assertTrue(InputValidator.tryParseDouble(input) == null);
    }
}
