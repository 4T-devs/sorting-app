package com.ftdevs.sortingapp.search;


import com.ftdevs.sortingapp.model.Product;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class BinarySearchUtilTest {

    private final Product[] products = {
            Product.builder().sku("A100").name("Laptop").price(1200.0).build(),
            Product.builder().sku("A200").name("Phone").price(800.0).build(),
            Product.builder().sku("A300").name("Tablet").price(500.0).build(),
            Product.builder().sku("A400").name("Monitor").price(300.0).build()
    };

    @Test
    void shouldFindBySku() {
        Arrays.sort(products, Comparator.comparing(Product::getSku));
        Product key = Product.builder().sku("A200").name("dummy").price(0.0).build();

        int idx = BinarySearchUtil.binarySearch(products, key, Comparator.comparing(Product::getSku));

        assertTrue(idx >= 0, "Product with SKU A200 should be found");
        assertEquals("Phone", products[idx].getName());
    }

    @Test
    void shouldFindByName() {
        Arrays.sort(products, Comparator.comparing(Product::getName));
        Product key = Product.builder().sku("dummy").name("Tablet").price(0.0).build();

        int idx = BinarySearchUtil.binarySearch(products, key, Comparator.comparing(Product::getName));

        assertTrue(idx >= 0, "Product with name Tablet should be found");
        assertEquals("A300", products[idx].getSku());
    }

    @Test
    void shouldFindByPrice() {
        Arrays.sort(products, Comparator.comparingDouble(Product::getPrice));
        Product key = Product.builder().sku("dummy").name("dummy").price(300.0).build();

        int idx = BinarySearchUtil.binarySearch(products, key, Comparator.comparingDouble(Product::getPrice));

        assertTrue(idx >= 0, "Product with price 300.0 should be found");
        assertEquals("Monitor", products[idx].getName());
    }

    @Test
    void shouldReturnMinusOneIfNotFound() {
        Arrays.sort(products, Comparator.comparing(Product::getSku));
        Product key = Product.builder().sku("Z999").name("dummy").price(0.0).build();

        int idx = BinarySearchUtil.binarySearch(products, key, Comparator.comparing(Product::getSku));

        assertEquals(-1, idx, "Unknown SKU should return -1");
    }
}

