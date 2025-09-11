package com.ftdevs.sortingapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

final class SorterMainTest {

    private SorterMainTest() {}

    @Test
    void printHelloWorld() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            SorterMain.main(new String[] {});
            assertEquals(
                    "Hello, World!" + System.lineSeparator(),
                    outContent.toString(),
                    "Output does not equal \"Hello, World!\"");
        } finally {
            System.setOut(originalOut);
        }
    }
}
