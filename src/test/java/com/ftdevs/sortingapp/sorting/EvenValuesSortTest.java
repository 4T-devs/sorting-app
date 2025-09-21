package com.ftdevs.sortingapp.sorting;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.comparator.ProductComparators;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.sorting.strategy.EvenValuesSort;
import com.ftdevs.sortingapp.sorting.strategy.QuickSort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

class EvenValuesSortTest {
    private EvenValuesSort<Product> evenSort;
    private CustomList<Product> actuaList;
    private Comparator<Product> productComparator = ProductComparators.BY_PRICE;

    @BeforeEach
    void init() {
        evenSort = new EvenValuesSort<>(new QuickSort<>(), (p) -> (int) p.getPrice());
        actuaList = new CustomArrayList<>();
    }

    @Test
    void testFewElements() {
        CustomList<Product> expectedList = fillList(actuaList);
        evenSort.sort(actuaList, productComparator);
        assertIterableEquals(expectedList, actuaList);
    }

    @Test
    void testOnlyEvens() {
        CustomList<Product> expectedList = fillOnlyEvens(actuaList);
        evenSort.sort(actuaList, productComparator);
        assertIterableEquals(expectedList, actuaList);
    }

    @Test
    void testOnlyOdds() {
        CustomList<Product> expectedList = fillOnlyOdds(actuaList);
        evenSort.sort(actuaList, productComparator);

        assertIterableEquals(expectedList, actuaList);
    }

    @Test
    void testOneEven() {
        CustomList<Product> expectedList = new CustomArrayList<>();
        CustomList<Product> actualList = new CustomArrayList<>();

        actualList.add(Product.builder().sku("1").name("1").price(30).build());
        expectedList.add(Product.builder().sku("1").name("1").price(30).build());
        evenSort.sort(actualList, productComparator);

        assertIterableEquals(expectedList, actualList);
    }

    @Test
    void testOneOdd() {
        CustomList<Product> expectedList = new CustomArrayList<>();
        CustomList<Product> actualList = new CustomArrayList<>();

        actualList.add(Product.builder().sku("1").name("1").price(31).build());
        evenSort.sort(actualList, productComparator);
        expectedList.add(Product.builder().sku("1").name("1").price(31).build());

        assertIterableEquals(expectedList, actualList);
    }

    @Test
    void testOneEvenOneOdd() {
        CustomList<Product> expectedList = new CustomArrayList<>();
        CustomList<Product> actualList = new CustomArrayList<>();

        actualList.add(Product.builder().sku("1").name("1").price(30).build());
        actualList.add(Product.builder().sku("1").name("1").price(35).build());
        evenSort.sort(actualList, productComparator);

        expectedList.add(Product.builder().sku("1").name("1").price(30).build());
        expectedList.add(Product.builder().sku("1").name("1").price(35).build());

        assertIterableEquals(expectedList, actualList);
    }


    /**
     *
     * @param list
     * @return возвращает отсортированный список(для проверки)
     */
    private CustomList<Product> fillList(CustomList<Product> list) {
        list.add(Product.builder().sku("1").name("1").price(30).build());
        list.add(Product.builder().sku("1").name("1").price(31).build());
        list.add(Product.builder().sku("1").name("1").price(10).build());
        list.add(Product.builder().sku("1").name("1").price(5).build());
        list.add(Product.builder().sku("1").name("1").price(37.55).build());
        list.add(Product.builder().sku("1").name("1").price(40.1).build());
        list.add(Product.builder().sku("1").name("1").price(14).build());
        list.add(Product.builder().sku("1").name("1").price(33).build());
        list.add(Product.builder().sku("1").name("1").price(7).build());

        CustomList<Product> expectedList = new CustomArrayList<>();
        expectedList.add(Product.builder().sku("1").name("1").price(10).build());
        expectedList.add(Product.builder().sku("1").name("1").price(31).build());
        expectedList.add(Product.builder().sku("1").name("1").price(14).build());
        expectedList.add(Product.builder().sku("1").name("1").price(5).build());
        expectedList.add(Product.builder().sku("1").name("1").price(37.55).build());
        expectedList.add(Product.builder().sku("1").name("1").price(30).build());
        expectedList.add(Product.builder().sku("1").name("1").price(40.1).build());
        expectedList.add(Product.builder().sku("1").name("1").price(33).build());
        expectedList.add(Product.builder().sku("1").name("1").price(7).build());

        return expectedList;
    }

    private CustomList<Product> fillOnlyEvens(CustomList<Product> list) {
        list.add(Product.builder().sku("1").name("1").price(30).build());
        list.add(Product.builder().sku("1").name("1").price(10).build());
        list.add(Product.builder().sku("1").name("1").price(6).build());
        list.add(Product.builder().sku("1").name("1").price(1000).build());
        list.add(Product.builder().sku("1").name("1").price(24).build());
        list.add(Product.builder().sku("1").name("1").price(36.33).build());
        list.add(Product.builder().sku("1").name("1").price(38.23).build());
        list.add(Product.builder().sku("1").name("1").price(18.5).build());
        list.add(Product.builder().sku("1").name("1").price(4.2).build());

        CustomList<Product> expectedList = new CustomArrayList<>();
        expectedList.add(Product.builder().sku("1").name("1").price(4.2).build());
        expectedList.add(Product.builder().sku("1").name("1").price(6).build());
        expectedList.add(Product.builder().sku("1").name("1").price(10).build());
        expectedList.add(Product.builder().sku("1").name("1").price(18.5).build());
        expectedList.add(Product.builder().sku("1").name("1").price(24).build());
        expectedList.add(Product.builder().sku("1").name("1").price(30).build());
        expectedList.add(Product.builder().sku("1").name("1").price(36.33).build());
        expectedList.add(Product.builder().sku("1").name("1").price(38.23).build());
        expectedList.add(Product.builder().sku("1").name("1").price(1000).build());

        return expectedList;
    }

    private CustomList<Product> fillOnlyOdds(CustomList<Product> list) {
        list.add(Product.builder().sku("1").name("1").price(7).build());
        list.add(Product.builder().sku("1").name("1").price(3).build());
        list.add(Product.builder().sku("1").name("1").price(11.33).build());
        list.add(Product.builder().sku("1").name("1").price(1.5).build());
        list.add(Product.builder().sku("1").name("1").price(21).build());
        list.add(Product.builder().sku("1").name("1").price(33).build());
        list.add(Product.builder().sku("1").name("1").price(17.3).build());
        list.add(Product.builder().sku("1").name("1").price(5.0001).build());
        list.add(Product.builder().sku("1").name("1").price(9.23).build());

        CustomList<Product> expectedList = new CustomArrayList<>();
        list.stream().forEach(expectedList::add);
        return expectedList;
    }

}
