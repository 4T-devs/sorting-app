package com.ftdevs.sortingapp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
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

        String input = "2\n4\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            SorterMain.main(new String[] {});
            assertTrue(outContent.toString().contains("END"), "End of program");
        } finally {
            System.setOut(originalOut);
        }
    }
}
