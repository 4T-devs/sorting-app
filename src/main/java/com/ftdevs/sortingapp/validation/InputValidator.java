package org.example.validation;

import java.text.NumberFormat;

public final class InputValidator {
    public static Integer tryParseInteger(String input){
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Float tryParseFloat(String input){
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Double tryParseDouble(String input){
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Long tryParseLong(String input){
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Byte tryParseByte(String input){
        try {
            return Byte.parseByte(input);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
