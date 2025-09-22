package com.ftdevs.sortingapp.context;

import static org.junit.jupiter.api.Assertions.*;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.comparator.ProductComparators;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.sorting.strategy.ISortStrategy;
import com.ftdevs.sortingapp.sorting.strategy.InsertionSorting;
import com.ftdevs.sortingapp.sorting.strategy.QuickSort;
import com.ftdevs.sortingapp.ui.context.SortingContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SortingContextTest {
    private static final String ARRAYS_NOT_EQUAL = "Arrays should be equals";
    private SortingContext<Product> sortingContext;
    private Comparator<Product> comparator;
    private CustomList<Product> productList;
    private List<Product> expectedProducts;

    @BeforeEach
    void setUp() {
        sortingContext = new SortingContext<>();
        comparator = ProductComparators.BY_NAME;
        productList = new CustomArrayList<>();
        expectedProducts = new ArrayList<>();
    }

    @Test
    @SuppressWarnings("PMD.UnitTestContainsTooManyAsserts")
    void testListWithoutStrategySort() {
        IllegalStateException ex =
                assertThrows(
                        IllegalStateException.class,
                        () -> sortingContext.sort(productList, Comparator.naturalOrder()));

        assertEquals(
                "Стратегия сортировки не установлена",
                ex.getMessage(),
                "Стратегия сортировки не установлена");
    }

    @Test
    void testSortListUsingInsertionStrategy() {
        productList.add(Product.builder().sku("SKU-123/123").name("Orange").price(60.0).build());
        productList.add(Product.builder().sku("SKU-123/123").name("Apple").price(50.0).build());
        productList.add(Product.builder().sku("SKU-123/123").name("Banana").price(40.0).build());
        expectedProducts.add(
                Product.builder().sku("SKU-123/123").name("Orange").price(60.0).build());
        expectedProducts.add(
                Product.builder().sku("SKU-123/123").name("Apple").price(50.0).build());
        expectedProducts.add(
                Product.builder().sku("SKU-123/123").name("Banana").price(40.0).build());
        ISortStrategy<Product> insertionStrategy = new InsertionSorting<>();
        sortingContext.setStrategy(insertionStrategy);
        sortingContext.sort(productList, comparator);
        expectedProducts.sort(comparator);
        assertIterableEquals(expectedProducts, productList, ARRAYS_NOT_EQUAL);
    }

    @Test
    void testSortListUsingQuickSortStrategy() {
        productList.add(Product.builder().sku("SKU-123/123").name("Orange").price(60.0).build());
        productList.add(Product.builder().sku("SKU-123/123").name("Apple").price(50.0).build());
        productList.add(Product.builder().sku("SKU-123/123").name("Banana").price(40.0).build());
        expectedProducts.add(
                Product.builder().sku("SKU-123/123").name("Orange").price(60.0).build());
        expectedProducts.add(
                Product.builder().sku("SKU-123/123").name("Apple").price(50.0).build());
        expectedProducts.add(
                Product.builder().sku("SKU-123/123").name("Banana").price(40.0).build());
        ISortStrategy<Product> insertionStrategy = new QuickSort<>();
        sortingContext.setStrategy(insertionStrategy);
        sortingContext.sort(productList, comparator);
        expectedProducts.sort(comparator);
        assertIterableEquals(expectedProducts, productList, ARRAYS_NOT_EQUAL);
    }

    @Test
    void stressTest() {
        ISortStrategy<Product> quickSort = new QuickSort<>();
        sortingContext.setStrategy(quickSort);

        int size = 1_000_000;
        for (int i = 0; i < size; i++) {
            Product p =
                    Product.builder()
                            .sku("SKU-" + i)
                            .name("Product-" + (int) (Math.random() * 1000)) // случайное имя
                            .price(Math.random() * 1000) // случайная цена
                            .build();
            productList.add(p);
        }

        sortingContext.sort(productList, comparator);

        Logger logger = Logger.getLogger(SortingContext.class.getName());
        TestLogHandler handler = new TestLogHandler();
        logger.addHandler(handler);

        sortingContext.sort(productList, comparator);

        // Проверяем нужный лог
        assertTrue(handler.getLastMessage().contains("Сортировка выполнена за"));

        logger.removeHandler(handler);
    }

    @Getter
    /* default */ static class TestLogHandler extends java.util.logging.Handler {
        private String lastMessage;

        @Override
        public void publish(java.util.logging.LogRecord record) {
            lastMessage = record.getMessage();
        }

        @Override
        public void flush() {}

        @Override
        public void close() {}
    }
}
