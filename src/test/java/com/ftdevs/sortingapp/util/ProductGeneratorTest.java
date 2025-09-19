package com.ftdevs.sortingapp.util;

import static org.junit.jupiter.api.Assertions.*;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.model.Product;
import org.junit.jupiter.api.Test;

final class ProductGeneratorTest {

    private ProductGeneratorTest() {}

    @Test
    void generateProductsCountCheck() {
        final int productCount = 5;

        final CustomList<Product> products = ProductGenerator.generateProducts(productCount);

        assertEquals(
                productCount,
                products.size(),
                "Должно быть создано указанное количество продуктов");
    }

    @Test
    void fillCollectionFilledProductsPassValidatorCheck() {
        final CustomArrayList<Product> collection = new CustomArrayList<>();
        final int productsToAdd = 50;

        ProductGenerator.fillCollection(collection, productsToAdd);
        assertDoesNotThrow(
                () ->
                        collection.forEach(
                                c ->
                                        ProductValidator.validateProduct(
                                                c.getSku(),
                                                c.getName(),
                                                String.valueOf(c.getPrice()))),
                "созданы невалидные объекты");
    }
}
