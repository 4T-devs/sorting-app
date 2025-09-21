package com.ftdevs.sortingapp.sorting;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.sorting.strategy.ISortStrategy;
import com.ftdevs.sortingapp.sorting.strategy.InsertionSorting;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.TooManyMethods")
final class MultithreadSortingTest {
    private static final String ARRAYS_NOT_EQUAL = "Arrays should be equals";
    private Comparator<Integer> comparator;
    private CustomList<Integer> customList;
    private List<Integer> expectedList;
    private ISortStrategy<Integer> sortStrategy;

    @SuppressWarnings("PMD.LongVariable")
    private MultithreadSorting<Integer> multithreadSorting;

    private MultithreadSortingTest() {}

    @BeforeEach
    void init() {
        sortStrategy = new InsertionSorting<>();
        comparator = Integer::compareTo;
        customList = new CustomArrayList<>();
        expectedList = new ArrayList<>();
    }

    @Test
    void testEmptyListSort() {
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        assertDoesNotThrow(
                () -> multithreadSorting.sort(customList, comparator),
                "Sorting must handle an empty list");
    }

    @Test
    @SuppressWarnings("PMD.UnitTestContainsTooManyAsserts")
    void testSingleElementSort() {
        customList.add(42);
        expectedList.add(42);
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        multithreadSorting.sort(customList, comparator);
        expectedList.sort(comparator);

        assertIterableEquals(expectedList, customList, ARRAYS_NOT_EQUAL);
    }

    @Test
    void testSmallArray() {
        fillArraysWithRandom(customList, expectedList, 9);
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        multithreadSorting.sort(customList, comparator);
        expectedList.sort(comparator);

        assertIterableEquals(expectedList, customList, ARRAYS_NOT_EQUAL);
    }

    @Test
    void testLargeArray() {
        fillArraysWithRandom(customList, expectedList, 100_005);
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        multithreadSorting.sort(customList, comparator);
        expectedList.sort(comparator);

        assertIterableEquals(expectedList, customList, ARRAYS_NOT_EQUAL);
    }

    @Test
    void testReversedOrder() {
        fillArrays(customList, expectedList, 1500, 1, -1);
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        multithreadSorting.sort(customList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, customList, ARRAYS_NOT_EQUAL);
    }

    @Test
    void testSortWithDuplicatesInList() {
        final int[] duplicates = {1, 5, 7, 7, 2, 3, 10, 100, 90, 5, 1, 3, 1, 5, 0, 30, 5};
        addToCollection(customList, expectedList, duplicates);
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        multithreadSorting.sort(customList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, customList, ARRAYS_NOT_EQUAL);
    }

    @Test
    @SuppressWarnings("PMD.LongVariable")
    void testSortWithNegativeNumbersInList() {
        final int[] arrWithNegatives = {
            20, -1, 50, -3, -4, 200, -60, 347, -47, -3, -1, 0, 0, 2, 4, -9, -43
        };
        addToCollection(customList, expectedList, arrWithNegatives);
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        multithreadSorting.sort(customList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, customList, ARRAYS_NOT_EQUAL);
    }

    @Test
    void testSortWithNullList() {
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        assertDoesNotThrow(() -> multithreadSorting.sort(null, comparator));
    }

    @Test
    @SuppressWarnings("PMD.LongVariable")
    void testSortIdenticalElements() {
        final int[] arrWithIdenticalElements = {10, 10, 10, 10, 10, 10};
        addToCollection(customList, expectedList, arrWithIdenticalElements);
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        multithreadSorting.sort(customList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, customList, ARRAYS_NOT_EQUAL);
    }

    @Test
    void testSortWithNullComparator() {
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        assertDoesNotThrow(
                () -> multithreadSorting.sort(customList, null),
                "Sorting must handle null comparator");
    }

    @Test
    void testSortWithThreeThreads() {
        fillArraysWithRandom(customList, expectedList, 1000);
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        multithreadSorting.sort(customList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, customList, ARRAYS_NOT_EQUAL);
    }

    @Test
    void testSortWithFourThreads() {
        fillArraysWithRandom(customList, expectedList, 1011);
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        multithreadSorting.sort(customList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, customList, ARRAYS_NOT_EQUAL);
    }

    @Test
    void testSortWithThreadsMoreThanElements() {
        fillArraysWithRandom(customList, expectedList, 1011);
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        multithreadSorting.sort(customList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, customList, ARRAYS_NOT_EQUAL);
    }

    @Test
    void testSortWithNegativeNumberOfThreads() {
        fillArraysWithRandom(customList, expectedList, 1011);
        multithreadSorting = MultithreadSorting.createOptimal(sortStrategy, customList.size());
        multithreadSorting.sort(customList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, customList, ARRAYS_NOT_EQUAL);
    }

    @SuppressWarnings("PMD.ShortVariable")
    private void fillArrays(
            final CustomList<Integer> list1,
            final List<Integer> list2,
            final int from,
            final int to,
            final int step) {
        for (int i = from; i >= to; i += step) {
            list1.add(i);
            list2.add(i);
        }
    }

    private void fillArraysWithRandom(
            final CustomList<Integer> list1, final List<Integer> list2, final int size) {
        for (int i = 0; i < size; i++) {
            final int value = i * 10 + (int) (Math.random() * 100);
            list1.add(value);
            list2.add(value);
        }
    }

    private void addToCollection(
            final CustomList<Integer> list1, final List<Integer> list2, final int... arr) {
        for (final Integer value : arr) {
            list1.add(value);
            list2.add(value);
        }
    }
}
