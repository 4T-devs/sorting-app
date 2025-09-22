package com.ftdevs.sortingapp.sorting;

import static org.junit.jupiter.api.Assertions.*;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.sorting.strategy.ISortStrategy;
import com.ftdevs.sortingapp.sorting.strategy.InsertionSorting;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.TooManyMethods")
final class InsertionSortingTest {

    private static final String INCORRECT_MSG = "Ошибка сортировки";
    private static final String SIZE_MISMATCH_MSG = "Неверный размер коллекции после сортировки";
    private final Comparator<Integer> comparator = Comparator.comparing(Integer::intValue);
    private ISortStrategy<Integer> insertionSort;
    private CustomArrayList<Integer> testCollection;

    private InsertionSortingTest() {}

    @BeforeEach
    void setUp() {
        insertionSort = new InsertionSorting<>();
        testCollection = new CustomArrayList<>();
    }

    @Test
    @DisplayName("Сортировка коллекции с одним элементом")
    @SuppressWarnings("PMD.UnitTestContainsTooManyAsserts")
    void testSortSingleElement() {
        testCollection.add(42);

        insertionSort.sort(testCollection, comparator);

        assertEquals(1, testCollection.size(), SIZE_MISMATCH_MSG);
        assertEquals(42, testCollection.get(0), "Единственный элемент должен остаться неизменным");
    }

    @Test
    @DisplayName("Сортировка пустой коллекции")
    @SuppressWarnings("PMD.UnitTestContainsTooManyAsserts")
    void testSortEmptyCollection() {
        assertDoesNotThrow(
                () -> insertionSort.sort(testCollection, comparator),
                "Метод должен корректно обрабатывать пустую коллекцию");
        assertEquals(0, testCollection.size(), "Пустая коллекция должна остаться пустой");
    }

    @Test
    @DisplayName("Сортировка коллекции")
    void testSortRandomCollection() {
        final Integer[] data = {2, 3, 45, 1, 20, -1, -11, 2};
        final List<Integer> expected = Arrays.stream(data).sorted().collect(Collectors.toList());

        addAllToCollection(data);
        insertionSort.sort(testCollection, comparator);

        assertIterableEquals(expected, testCollection, INCORRECT_MSG);
    }

    @Test
    @DisplayName("Сортировка коллекции с дубликатами")
    void testSortWithDuplicates() {
        final Integer[] data = {12, 15, 15, 12, 1, 0};
        final List<Integer> expected = Arrays.stream(data).sorted().collect(Collectors.toList());

        addAllToCollection(data);
        insertionSort.sort(testCollection, comparator);

        assertIterableEquals(expected, testCollection, INCORRECT_MSG);
    }

    @Test
    @DisplayName("Сортировка коллекции с отрицательными числами")
    void testSortWithNegativeNumbers() {
        final Integer[] data = {-5, 10, -3, 0, 7, -1};
        final List<Integer> expected = Arrays.stream(data).sorted().collect(Collectors.toList());

        addAllToCollection(data);
        insertionSort.sort(testCollection, comparator);

        assertIterableEquals(expected, testCollection, INCORRECT_MSG);
    }

    @Test
    @DisplayName("Сортировка уже отсортированной коллекции")
    void testSortAlreadySorted() {
        final Integer[] data = {1, 2, 3, 4, 5};
        final List<Integer> expected = Arrays.asList(data);

        addAllToCollection(data);
        insertionSort.sort(testCollection, comparator);

        assertIterableEquals(
                expected,
                testCollection,
                "Уже отсортированная коллекция должна остаться неизменной");
    }

    @Test
    @DisplayName("Сортировка коллекции в обратном порядке")
    void testSortReverseOrder() {
        final Integer[] data = {5, 4, 3, 2, 1};
        final List<Integer> expected = Arrays.stream(data).sorted().collect(Collectors.toList());

        addAllToCollection(data);
        insertionSort.sort(testCollection, comparator);

        assertIterableEquals(expected, testCollection, INCORRECT_MSG);
    }

    @Test
    @DisplayName("Сортировка null коллекции")
    void testSortNullCollection() {
        assertDoesNotThrow(
                () -> insertionSort.sort(null, comparator),
                "Метод должен корректно обрабатывать null коллекцию");
    }

    @Test
    @DisplayName("Сортировка коллекции с одинаковыми элементами")
    void testSortAllSameElements() {
        final Integer[] data = {5, 5, 5, 5, 5};
        final List<Integer> expected = Arrays.asList(data);

        addAllToCollection(data);
        insertionSort.sort(testCollection, comparator);

        assertIterableEquals(
                expected,
                testCollection,
                "Коллекция с одинаковыми элементами должна остаться неизменной");
    }

    private void addAllToCollection(final Integer... data) {
        for (final Integer value : data) {
            testCollection.add(value);
        }
    }
}
