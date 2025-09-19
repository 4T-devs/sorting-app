package com.ftdevs.sortingapp.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ftdevs.sortingapp.model.Product;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

final class ProductComparatorsTest {

    private ProductComparatorsTest() {}

    @Test
    void shouldSortBySku() {
        final List<Product> products = new ArrayList<>();
        products.add(Product.builder().sku("B200").name("Phone").price(500).build());
        products.add(Product.builder().sku("A100").name("Laptop").price(1200).build());

        products.sort(ProductComparators.BY_SKU);

        assertEquals("A100", products.get(0).getSku(), "Первый должен быть A100");
    }

    @Test
    void shouldSortByName() {
        final List<Product> products = new ArrayList<>();
        products.add(Product.builder().sku("C300").name("Tablet").price(800).build());
        products.add(Product.builder().sku("A100").name("Laptop").price(1200).build());

        products.sort(ProductComparators.BY_NAME);

        assertEquals("Laptop", products.get(0).getName(), "Laptop должен быть первым");
    }

    @Test
    void shouldSortByPrice() {
        final List<Product> products = new ArrayList<>();
        products.add(Product.builder().sku("C300").name("Tablet").price(800).build());
        products.add(Product.builder().sku("A100").name("Laptop").price(300).build());

        products.sort(ProductComparators.BY_PRICE);

        assertEquals(300.0, products.get(0).getPrice(), "Сначала должен быть Laptop с 300");
    }

    @Test
    void shouldSortByCustomPriceComparator() {
        final List<Product> products = new ArrayList<>();
        products.add(Product.builder().sku("C300").name("Tablet").price(801).build()); // нечётная
        products.add(Product.builder().sku("A100").name("Laptop").price(300).build()); // чётная

        products.sort(ProductComparators.CUSTOM_PRICE);

        assertEquals(300.0, products.get(0).getPrice(), "Четные цены должны идти первыми");
    }

    @Test
    void shouldSortByGeneralComparator() {
        final List<Product> products = new ArrayList<>();
        products.add(Product.builder().sku("C300").name("Phone").price(500).build());
        products.add(Product.builder().sku("A100").name("Phone").price(500).build());

        products.sort(ProductComparators.GENERAL);

        assertEquals("A100", products.get(0).getSku(), "При равных имени и цене сортируем по SKU");
    }

    @Test
    void shouldSortByCustomGeneralComparator() {
        final List<Product> products = new ArrayList<>();
        products.add(Product.builder().sku("C300").name("Tablet").price(801).build()); // нечётная
        products.add(Product.builder().sku("B200").name("Phone").price(1200).build()); // чётная
        products.add(Product.builder().sku("A100").name("Laptop").price(300).build()); // чётная
        products.add(Product.builder().sku("D400").name("Monitor").price(501).build()); // нечётная

        products.sort(ProductComparators.CUSTOM_GENERAL);

        assertEquals(300.0, products.get(0).getPrice(), "Четные должны идти первыми (Laptop)");
    }
}
