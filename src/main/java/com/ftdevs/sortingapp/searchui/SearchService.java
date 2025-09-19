package com.ftdevs.sortingapp.searchui;

import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.comparator.ProductComparators;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.search.BinarySearchUtil;
import java.util.Optional;

public final class SearchService {

    private SearchService() {}

    public static Optional<Integer> findBySku(
            final CustomList<Product> products, final String sku) {
        final Product key = Product.builder().sku(sku).name("dummy").price(0.0).build();
        final int idx = BinarySearchUtil.binarySearch(products, key, ProductComparators.BY_SKU);
        return idx >= 0 ? Optional.of(idx) : Optional.empty();
    }

    public static Optional<Integer> findByName(
            final CustomList<Product> products, final String name) {
        final Product key = Product.builder().sku("dummy").name(name).price(0.0).build();
        final int idx = BinarySearchUtil.binarySearch(products, key, ProductComparators.BY_NAME);
        return idx >= 0 ? Optional.of(idx) : Optional.empty();
    }

    public static Optional<Integer> findByPrice(
            final CustomList<Product> products, final double price) {
        final Product key = Product.builder().sku("dummy").name("dummy").price(price).build();
        final int idx = BinarySearchUtil.binarySearch(products, key, ProductComparators.BY_PRICE);
        return idx >= 0 ? Optional.of(idx) : Optional.empty();
    }
}
