package com.ftdevs.sortingapp.searchui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.model.Product;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class BinarySearchConsoleTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream ORIGINAL_OUT = System.out;

    private BinarySearchConsoleTest() {}

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(ORIGINAL_OUT);
    }

    private CustomList<Product> createSortedProducts() {
        final CustomArrayList<Product> products = new CustomArrayList<>();
        products.add(Product.builder().sku("A100").name("Laptop").price(300.0).build());
        products.add(Product.builder().sku("A200").name("Phone").price(500.0).build());
        products.add(Product.builder().sku("A300").name("Tablet").price(800.0).build());
        products.add(Product.builder().sku("A400").name("Monitor").price(1200.0).build());
        return products;
    }

    @Test
    void shouldFindProductBySku() {
        final String input = "1\nA200\n0\n"; // выбор 1 (поиск по SKU), ввод A200, потом выход
        final Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        BinarySearchConsole.binarySearch(createSortedProducts(), scanner);
        final String output = outContent.toString();
        assertTrue(output.contains("Phone"), "Should return Phone");
    }

    @Test
    void shouldFindProductByName() {
        final String input = "2\nTablet\n0\n"; // поиск по имени: ввод Tablet → выход
        final Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        BinarySearchConsole.binarySearch(createSortedProducts(), scanner);
        final String output = outContent.toString();
        assertTrue(output.contains("Tablet"), "Should return Tablet");
    }

    @Test
    void shouldFindProductByPrice() {
        final String input = "3\n300\n0\n"; // поиск по цене: ввод 300 → выход
        final Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        BinarySearchConsole.binarySearch(createSortedProducts(), scanner);
        final String output = outContent.toString();
        assertTrue(output.contains("Laptop"), "Should return Laptop");
    }

    @Test
    void shouldReturnNotFoundMessage() {
        final String input = "1\nZ999\n0\n"; // поиск по SKU, которого нет
        final Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        BinarySearchConsole.binarySearch(createSortedProducts(), scanner);
        final String output = outContent.toString();
        assertTrue(output.contains("Элемент не найден."), "Should return not found");
    }

    @Test
    void shouldExitOnZero() {
        final String input = "0\n";
        final Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        BinarySearchConsole.binarySearch(createSortedProducts(), scanner);
        final String output = outContent.toString();
        assertTrue(output.contains("Выход из поиска."), "Should return out");
    }
}
