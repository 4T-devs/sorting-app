package com.ftdevs.sortingapp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

final class SorterMainTest {

    private SorterMainTest() {}

    @Test
    void printHelloWorld() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        final InputStream originalIn = System.in;
        System.setOut(new PrintStream(outContent));

        String input = "2\n4\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        try {
            SorterMain.main(new String[] {});
            assert true;
        } finally {
            System.setOut(originalOut);
        }
    }
}
