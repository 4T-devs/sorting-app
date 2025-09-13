package com.ftdevs.sortingapp.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;
import lombok.AllArgsConstructor;

@Builder(builderClassName = "ProductBuilder")
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Product {
    String sku;
    String name;
    double price;

    public static class ProductBuilder {
        public Product build() {
            if (sku == null || sku.isBlank()) {
                throw new IllegalArgumentException("SKU cannot be null or blank");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be null or blank");
            }
            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            return new Product(sku, name, price);
        }
    }
}
