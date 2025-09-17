package com.ftdevs.sortingapp.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ftdevs.sortingapp.model.Product;
import java.util.Arrays;
import java.util.Comparator;
import org.junit.jupiter.api.Test;

final class BinarySearchUtilTest {

    private final Product[] products = {
        Product.builder().sku("A100").name("Laptop").price(1200.0).build(),
        Product.builder().sku("A200").name("Phone").price(800.0).build(),
        Product.builder().sku("A300").name("Tablet").price(500.0).build(),
        Product.builder().sku("A400").name("Monitor").price(300.0).build()
    };

    private BinarySearchUtilTest() {}

    @Test
    void shouldFindBySkuIndexFound() {
        Arrays.sort(products, Comparator.comparing(Product::getSku));
        final Product key = Product.builder().sku("A200").name("dummy").price(0.0).build();

        final int idx =
                BinarySearchUtil.binarySearch(products, key, Comparator.comparing(Product::getSku));

        assertTrue(idx >= 0, "Product with SKU A200 should be found");
    }

    @Test
    void shouldFindBySkuCorrectName() {
        Arrays.sort(products, Comparator.comparing(Product::getSku));
        final Product key = Product.builder().sku("A200").name("dummy").price(0.0).build();

        final int idx =
                BinarySearchUtil.binarySearch(products, key, Comparator.comparing(Product::getSku));

        assertEquals("Phone", products[idx].getName(), "Product with SKU A200 should be Phone");
    }

    @Test
    void shouldFindByNameIndexFound() {
        Arrays.sort(products, Comparator.comparing(Product::getName));
        final Product key = Product.builder().sku("dummy").name("Tablet").price(0.0).build();

        final int idx =
                BinarySearchUtil.binarySearch(
                        products, key, Comparator.comparing(Product::getName));

        assertTrue(idx >= 0, "Product with name Tablet should be found");
    }

    @Test
    void shouldFindByNameCorrectSku() {
        Arrays.sort(products, Comparator.comparing(Product::getName));
        final Product key = Product.builder().sku("dummy").name("Tablet").price(0.0).build();

        final int idx =
                BinarySearchUtil.binarySearch(
                        products, key, Comparator.comparing(Product::getName));

        assertEquals("A300", products[idx].getSku(), "Tablet should have SKU A300");
    }

    @Test
    void shouldFindByPriceIndexFound() {
        Arrays.sort(products, Comparator.comparingDouble(Product::getPrice));
        final Product key = Product.builder().sku("dummy").name("dummy").price(300.0).build();

        final int idx =
                BinarySearchUtil.binarySearch(
                        products, key, Comparator.comparingDouble(Product::getPrice));

        assertTrue(idx >= 0, "Product with price 300.0 should be found");
    }

    @Test
    void shouldFindByPriceCorrectName() {
        Arrays.sort(products, Comparator.comparingDouble(Product::getPrice));
        final Product key = Product.builder().sku("dummy").name("dummy").price(300.0).build();

        final int idx =
                BinarySearchUtil.binarySearch(
                        products, key, Comparator.comparingDouble(Product::getPrice));

        assertEquals(
                "Monitor", products[idx].getName(), "Product with price 300.0 should be Monitor");
    }

    @Test
    void shouldReturnMinusOneIfNotFound() {
        Arrays.sort(products, Comparator.comparing(Product::getSku));
        final Product key = Product.builder().sku("Z999").name("dummy").price(0.0).build();

        final int idx =
                BinarySearchUtil.binarySearch(products, key, Comparator.comparing(Product::getSku));

        assertEquals(-1, idx, "Unknown SKU should return -1");
    }
}
