package com.ftdevs.sortingapp.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

final class InputValidatorTest {

    private InputValidatorTest() {}

    @Test
    void parseIntegerTest() {
        String input = "1";
        assertEquals(1, InputValidator.tryParseInteger(input), "Value is valid");
    }

    @Test
    void parseLongTest() {
        String input = "2";
        assertEquals(2, InputValidator.tryParseLong(input), "Value is valid");
    }

    @Test
    void parseFloatTest() {
        String input = "3.33";
        assertEquals(3.33f, InputValidator.tryParseFloat(input), "Value is valid");
    }

    @Test
    void parseDoubleTest() {
        String input = "4.44";
        assertEquals(4.44d, InputValidator.tryParseDouble(input), "Value is valid");
    }

    @Test
    void nonValidIntegerTest() {
        String input = "qwerty";
        assertNull(InputValidator.tryParseInteger(input), "Invalid value is processed");
    }

    @Test
    void nonValidLongTest() {
        String input = "qwerty";
        assertNull(InputValidator.tryParseLong(input), "Invalid value is processed");
    }

    @Test
    void nonValidFloatTest() {
        String input = "qwerty";
        assertNull(InputValidator.tryParseFloat(input), "Invalid value is processed");
    }

    @Test
    void nonValidDoubleTest() {
        String input = "qwerty";
        assertNull(InputValidator.tryParseDouble(input), "Invalid value is processed");
    }
}
