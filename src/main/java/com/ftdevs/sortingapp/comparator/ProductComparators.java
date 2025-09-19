package com.ftdevs.sortingapp.comparator;

import com.ftdevs.sortingapp.model.Product;
import java.util.Comparator;

public final class ProductComparators {

    public static final Comparator<Product> BY_SKU = Comparator.comparing(Product::getSku);

    public static final Comparator<Product> BY_NAME = Comparator.comparing(Product::getName);

    public static final Comparator<Product> BY_PRICE =
            Comparator.comparingDouble(Product::getPrice);

    public static final Comparator<Product> CUSTOM_PRICE =
            (p1, p2) -> {
                final boolean p1Even = isPriceEven(p1);
                final boolean p2Even = isPriceEven(p2);

                if (p1Even && p2Even) {
                    return Double.compare(p1.getPrice(), p2.getPrice());
                }
                return p1Even
                        ? -1
                        : p2Even ? 1 : 0; // если первое четное: -1, если второе: 1,если оба: 0
            };

    public static final Comparator<Product> GENERAL =
            Comparator.comparing(Product::getName)
                    .thenComparing(Product::getPrice)
                    .thenComparing(Product::getSku);

    public static final Comparator<Product> CUSTOM_GENERAL =
            (p1, p2) -> {
                final boolean even1 = isPriceEven(p1);
                final boolean even2 = isPriceEven(p2);

                if (even1 && even2) {
                    return GENERAL.compare(p1, p2);
                }
                return even1
                        ? -1
                        : even2 ? 1 : 0; // если первое четное: -1, если второе: 1,если оба: 0
            };

    private ProductComparators() {}

    public static boolean isPriceEven(final Product product) {
        return (int) product.getPrice() % 2 == 0;
    }
}
