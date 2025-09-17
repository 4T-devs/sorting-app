package com.ftdevs.sortingapp.search;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ftdevs.sortingapp.model.Product;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

final class BinarySearchApplicationTest {

    private BinarySearchApplicationTest() {}

    private Product[] createProducts() {
        return new Product[] {
            Product.builder().sku("A100").name("Laptop").price(1200).build(),
            Product.builder().sku("A200").name("Phone").price(800).build(),
            Product.builder().sku("A300").name("Tablet").price(500).build(),
            Product.builder().sku("A400").name("Monitor").price(300).build()
        };
    }

    private String runWithInput(final Product[] products, final String input) {
        final Scanner scanner = new Scanner(input);

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            BinarySearchApplication.binarySearch(products, scanner);
            return outContent.toString();
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void shouldFindProductBySkuThroughConsoleContainsProduct() {
        final String input = "1\nA200\n0\n";
        final String output = runWithInput(createProducts(), input);

        assertTrue(output.contains("Phone"), "Output should contain found product 'Phone'");
    }

    @Test
    void shouldFindProductBySkuThroughConsoleContainsExitMessage() {
        final String input = "1\nA200\n0\n";
        final String output = runWithInput(createProducts(), input);

        assertTrue(output.contains("Выход из поиска."), "Output should contain exit message");
    }

    @Test
    void shouldFindProductByNameThroughConsoleContainsProduct() {
        final String input = "2\nTablet\n0\n";
        final String output = runWithInput(createProducts(), input);

        assertTrue(output.contains("Tablet"), "Output should contain found product 'Tablet'");
    }

    @Test
    void shouldFindProductByNameThroughConsoleContainsExitMessage() {
        final String input = "2\nTablet\n0\n";
        final String output = runWithInput(createProducts(), input);

        assertTrue(output.contains("Выход из поиска."), "Output should contain exit message");
    }

    @Test
    void shouldFindProductByPriceThroughConsoleContainsProduct() {
        final String input = "3\n300.0\n0\n";
        final String output = runWithInput(createProducts(), input);

        assertTrue(output.contains("Monitor"), "Output should contain found product 'Monitor'");
    }

    @Test
    void shouldFindProductByPriceThroughConsoleContainsExitMessage() {
        final String input = "3\n300.0\n0\n";
        final String output = runWithInput(createProducts(), input);

        assertTrue(output.contains("Выход из поиска."), "Output should contain exit message");
    }
}
