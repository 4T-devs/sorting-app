package com.ftdevs.sortingapp.searchui;

import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.comparator.ProductComparators;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.search.BinarySearchUtil;
import com.ftdevs.sortingapp.sorting.strategy.QuickSort;
import java.util.Optional;

public final class SearchService {
    private static QuickSort<Product> quickSort = new QuickSort<>();

    private SearchService() {}

    public static Optional<Integer> findBySku(
            final CustomList<Product> products, final String sku) {
        quickSort.sort(products, ProductComparators.BY_SKU);
        final Product key = Product.builder().sku(sku).name("dummy").price(0.0).build();
        final int idx = BinarySearchUtil.binarySearch(products, key, ProductComparators.BY_SKU);
        return idx >= 0 ? Optional.of(idx) : Optional.empty();
    }

    public static Optional<Integer> findByName(
            final CustomList<Product> products, final String name) {
        quickSort.sort(products, ProductComparators.BY_NAME);
        final Product key = Product.builder().sku("dummy").name(name).price(0.0).build();
        final int idx = BinarySearchUtil.binarySearch(products, key, ProductComparators.BY_NAME);
        return idx >= 0 ? Optional.of(idx) : Optional.empty();
    }

    public static Optional<Integer> findByPrice(
            final CustomList<Product> products, final double price) {
        quickSort.sort(products, ProductComparators.BY_PRICE);
        final Product key = Product.builder().sku("dummy").name("dummy").price(price).build();
        final int idx = BinarySearchUtil.binarySearch(products, key, ProductComparators.BY_PRICE);
        return idx >= 0 ? Optional.of(idx) : Optional.empty();
    }
}
