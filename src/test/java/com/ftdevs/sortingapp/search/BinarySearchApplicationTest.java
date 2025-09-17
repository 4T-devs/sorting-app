package com.ftdevs.sortingapp.search;

import com.ftdevs.sortingapp.model.Product;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class BinarySearchApplicationTest {

    private Product[] createProducts() {
        return new Product[]{
                Product.builder().sku("A100").name("Laptop").price(1200).build(),
                Product.builder().sku("A200").name("Phone").price(800).build(),
                Product.builder().sku("A300").name("Tablet").price(500).build(),
                Product.builder().sku("A400").name("Monitor").price(300).build()
        };
    }

    private String runWithInput(Product[] products, String input) {
        Scanner scanner = new Scanner(input);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            BinarySearchApplication.binarySearch(products, scanner);
            return outContent.toString();
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void shouldFindProductBySkuThroughConsole() {
        String input = "1\nA200\n0\n"; // выбор поля SKU -> ввод A200 -> выход
        String output = runWithInput(createProducts(), input);

        assertTrue(output.contains("Phone"), "Output should contain found product 'Phone'");
        assertTrue(output.contains("Выход из поиска."), "Output should contain exit message");
    }

    @Test
    void shouldFindProductByNameThroughConsole() {
        String input = "2\nTablet\n0\n"; // выбор поля Name -> ввод Tablet -> выход
        String output = runWithInput(createProducts(), input);

        assertTrue(output.contains("Tablet"), "Output should contain found product 'Tablet'");
        assertTrue(output.contains("Выход из поиска."), "Output should contain exit message");
    }

    @Test
    void shouldFindProductByPriceThroughConsole() {
        String input = "3\n300.0\n0\n"; // выбор поля Price -> ввод 300.0 -> выход
        String output = runWithInput(createProducts(), input);

        assertTrue(output.contains("Monitor"), "Output should contain found product 'Monitor'");
        assertTrue(output.contains("Выход из поиска."), "Output should contain exit message");
    }
}

