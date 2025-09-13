package com.ftdevs.sortingapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

final class ProductTest {

    private ProductTest() {}

    @Test
    void shouldHaveCorrectSku() {
        final Product product = Product.builder().sku("123").name("Product1").price(99.99).build();
        assertEquals("123", product.getSku(), "SKU does not match expected value");
    }

    @Test
    void shouldHaveCorrectName() {
        final Product product = Product.builder().sku("123").name("Product1").price(99.99).build();
        assertEquals("Product1", product.getName(), "Name does not match expected value");
    }

    @Test
    void shouldHaveCorrectPrice() {
        final Product product = Product.builder().sku("123").name("Product1").price(99.99).build();
        assertEquals(99.99, product.getPrice(), "Price does not match expected value");
    }

    @Test
    void shouldThrowExceptionWhenSkuIsBlank() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Product.builder().sku("").name("Product2").price(99.99).build(),
                "Expected exception for blank SKU");
    }

    @Test
    void shouldThrowExceptionWhenNameIsBlank() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Product.builder().sku("111").name(" ").price(99.99).build(),
                "Expected exception for blank Name");
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> Product.builder().sku("222").name("Product3").price(-10.0).build(),
                "Expected exception for negative Price");
    }
}
