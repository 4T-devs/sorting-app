package com.ftdevs.sortingapp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

final class ProductSortingApplicationTest {

    @Test
    void applicationRunTest() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        IOSingleton.getInstance().setOutput(originalOut);

        String input = "0";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            ProductSortingApplication.main(new String[] {});
            assertTrue(
                    outContent.toString().contains("Exit code 0"),
                    "Expected \"Exit code 0\"\nbut was " + outContent.toString());
        } finally {
            System.setOut(originalOut);
        }
    }
}
