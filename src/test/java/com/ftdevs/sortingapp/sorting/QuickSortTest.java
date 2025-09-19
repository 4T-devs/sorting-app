package com.ftdevs.sortingapp.sorting;

import static org.junit.jupiter.api.Assertions.*;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.model.Product;
import java.util.Comparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.UnitTestContainsTooManyAsserts")
final class QuickSortTest {

    private CustomList<Product> products;
    private QuickSort<Product> quickSort;

    private QuickSortTest() {}

    @BeforeEach
    void setUp() {
        products = new CustomArrayList<>();
        quickSort = new QuickSort<>();
    }

    @Test
    void sortEmptyList() {
        quickSort.sort(products, Comparator.comparing(Product::getPrice));
        assertEquals(
                0, products.size(), "Список должен быть пустым после сортировки пустого списка");
    }

    @Test
    void sortSingleElement() {
        final Product product =
                Product.builder().sku("ABC-000/001").name("Apple").price(999.99).build();
        products.add(product);
        quickSort.sort(products, Comparator.comparing(Product::getPrice));
        assertEquals(1, products.size(), "Список должен содержать один элемент после сортировки");
        assertEquals(product, products.get(0), "Элемент должен остаться неизменным");
    }

    @Test
    void sortByPriceAscending() {
        products.add(Product.builder().sku("ABC-000/001").name("Banana").price(300).build());
        products.add(Product.builder().sku("ABC-000/002").name("Orange").price(50).build());
        products.add(Product.builder().sku("ABC-000/003").name("Mango").price(200).build());

        quickSort.sort(products, Comparator.comparing(Product::getPrice));

        assertEquals(
                50, products.get(0).getPrice(), "Первый элемент должен иметь минимальную цену");
        assertEquals(200, products.get(1).getPrice(), "Второй элемент должен иметь среднюю цену");
        assertEquals(
                300, products.get(2).getPrice(), "Третий элемент должен иметь максимальную цену");
    }

    @Test
    void sortByPriceDescending() {
        products.add(Product.builder().sku("ABC-000/001").name("Banana").price(300).build());
        products.add(Product.builder().sku("ABC-000/002").name("Orange").price(50).build());
        products.add(Product.builder().sku("ABC-000/003").name("Mango").price(200).build());

        quickSort.sort(products, Comparator.comparing(Product::getPrice).reversed());

        assertEquals(
                300, products.get(0).getPrice(), "Первый элемент должен иметь максимальную цену");
        assertEquals(200, products.get(1).getPrice(), "Второй элемент должен иметь среднюю цену");
        assertEquals(
                50, products.get(2).getPrice(), "Третий элемент должен иметь минимальную цену");
    }

    @Test
    void sortByName() {
        products.add(Product.builder().sku("ABC-000/001").name("Milk").price(300).build());
        products.add(Product.builder().sku("ABC-000/002").name("Yogurt").price(50).build());
        products.add(Product.builder().sku("ABC-000/003").name("Cheese").price(200).build());

        quickSort.sort(products, Comparator.comparing(Product::getName));

        assertEquals("Cheese", products.get(0).getName(), "Сортировка по алфавиту (C)");
        assertEquals("Milk", products.get(1).getName(), "Сортировка по алфавиту (M)");
        assertEquals("Yogurt", products.get(2).getName(), "Сортировка по алфавиту (Y)");
    }

    @Test
    void sortWithDuplicates() {
        products.add(Product.builder().sku("ABC-000/001").name("Milk").price(300).build());
        products.add(Product.builder().sku("ABC-000/001").name("Milk").price(300).build());
        products.add(Product.builder().sku("ABC-000/002").name("Yogurt").price(50).build());

        quickSort.sort(products, Comparator.comparing(Product::getPrice));

        assertEquals(
                50, products.get(0).getPrice(), "Первый элемент должен иметь минимальную цену");
        assertEquals(
                300, products.get(1).getPrice(), "Второй элемент должен иметь максимальную цену");
        assertEquals(
                300, products.get(2).getPrice(), "Третий элемент должен иметь максимальную цену");
    }

    @Test
    void sortLargeList() {
        for (int i = 1000; i >= 1; i--) {
            final String sku = String.format("ABC-%03d/%03d", i / 1000, i % 1000);
            products.add(Product.builder().sku(sku).name("Product" + i).price(i).build());
        }

        quickSort.sort(products, Comparator.comparing(Product::getPrice));

        for (int i = 0; i < 1000; i++) {
            assertEquals(
                    i + 1,
                    products.get(i).getPrice(),
                    "Элемент " + i + " должен иметь цену " + (i + 1));
        }
    }
}
