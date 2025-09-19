package com.ftdevs.sortingapp.entityCreators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

final class HandInputTest {

    private HandInputTest() {}

    @Test
    void handInputTest() {

        String input = "2";
        String iStream = "10232234\nproduct1\n3.33\nstop";
        InputStream testInput = new ByteArrayInputStream(iStream.getBytes());

        System.setOut(System.out);
        System.setIn(testInput);

        HandInput hi = new HandInput();
        var result = hi.createProducts(input);

        String[] args = iStream.split("\n");
        boolean testResult = true;
        String message = "";

        if (!result.get(0).getSku().equals(args[0])) {
            testResult = false;
            message =
                    String.format("Expected SKU: %s, but was %s", result.get(0).getSku(), args[0]);
        }
        if (!result.get(0).getName().equals(args[1])) {
            testResult = false;
            message =
                    String.format(
                            "Expected name: %s, but was %s", result.get(0).getName(), args[1]);
        }
        if (!String.valueOf(result.get(0).getPrice()).equals(args[2])) {
            testResult = false;
            message =
                    String.format(
                            "Expected price: %f, but was %s", result.get(0).getPrice(), args[2]);
        }
        assertTrue(testResult, message);
    }
}
