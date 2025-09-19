package com.ftdevs.sortingapp.model;

import lombok.*;

@Value
public final class Product implements Comparable<Product> {
    String sku;
    String name;
    double price;

    private Product(final Builder builder) {
        this.sku = builder.sku;
        this.name = builder.name;
        this.price = builder.price;
    }

    @Override
    public int compareTo(final Product other) {
        return this.sku.compareTo(other.sku);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String sku;
        private String name;
        private double price;

        public Builder sku(final String sku) {
            this.sku = sku;
            return this;
        }

        public Builder name(final String name) {
            this.name = name;
            return this;
        }

        public Builder price(final double price) {
            this.price = price;
            return this;
        }

        public Product build() {
            if (sku == null || sku.trim().isBlank()) {
                throw new IllegalArgumentException("SKU cannot be null or blank");
            }
            if (name == null || name.trim().isBlank()) {
                throw new IllegalArgumentException("Name cannot be null or blank");
            }
            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
            return new Product(this);
        }
    }
}
