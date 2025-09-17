package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


class ParallelSortingTest {
    private ISortStrategy<Integer> sortStrategy;
    private Comparator<Integer> comparator;
    private CustomList<Integer> customList;
    private ParallelSorting<Integer> parallelSorting;

    private ParallelSortingTest() {}

    @BeforeEach
    void init() {
        sortStrategy = new InsertionSorting<>();
        comparator = Integer::compareTo;
        customList = new CustomArrayList<>();
        final int threshold = 10;
        parallelSorting = new ParallelSorting<>(sortStrategy, threshold);
    }

    @Test
    void testEmptyListSort() {
        assertDoesNotThrow(() -> parallelSorting.sort(customList, comparator));
    }

    @Test
    void testSingleElementSort() {
        customList.add(42);
        parallelSorting.sort(customList, comparator);

        assertEquals(1, customList.size());
        assertEquals(42, customList.get(0));
    }

    @Test
    void testSizeLessThanThreshold() {
        List<Integer> expectedList = new ArrayList<>();
        fillArraysWithRandom(customList, expectedList, 9);
        parallelSorting.sort(customList, comparator);
        expectedList.sort(comparator);

        assertIterableEquals(expectedList, customList);
    }

    @Test
    void testSizeGreaterThanThreshold() {
        List<Integer> expectedList = new ArrayList<>();
        fillArraysWithRandom(customList, expectedList, 100_005);
        parallelSorting.sort(customList, comparator);
        expectedList.sort(comparator);

        assertIterableEquals(expectedList, customList);
    }

    @Test
    void testReversedOrder() {
        List<Integer> expectedList = new ArrayList<>();
        CustomList<Integer> actualList = new CustomArrayList<>();
        fillArrays(actualList, expectedList, 1500, 1, -1);

        parallelSorting.sort(actualList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, actualList);
    }

    @Test
    void testSortWithDuplicatesInList() {
        final int[] duplicates = new int[] {1, 5, 7,7, 2, 3, 10, 100, 90, 5, 1, 3, 1, 5, 0, 30, 5};
        List<Integer> expectedList = new ArrayList<>();

        addToCollection(customList, expectedList, duplicates);
        parallelSorting.sort(customList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, customList);
    }

    @Test
    void testSortWithNegativeNumbersInList() {
        final int[] arrWithNegatives = new int[]{20, -1, 50, -3, -4, 200, -60, 347, -47, -3, -1, 0, 0, 2, 4, -9, -43};
        List<Integer> expectedList = new ArrayList<>();

        addToCollection(customList, expectedList, arrWithNegatives);

        parallelSorting.sort(customList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, customList);
    }

    @Test
    void testSortWithNullList() {
        assertDoesNotThrow(() -> parallelSorting.sort(null, comparator));
    }

    @Test
    void testSortIdenticalElements() {
        final int[] arrWithIdenticalElements = new int[] {10,10,10,10,10,10};
        List<Integer> expectedList = new ArrayList<>();
        addToCollection(customList, expectedList,arrWithIdenticalElements);
        parallelSorting.sort(customList, comparator);
        expectedList.sort(comparator);
        assertIterableEquals(expectedList, customList);
    }

    @Test
    void sortWithNullComparator() {
        assertDoesNotThrow(() -> parallelSorting.sort(customList, null));
    }


    private void fillArrays(final CustomList<Integer> list1, List<Integer> list2, final int from, final int to, final int step) {
        for(int i = from; i >= to; i += step) {
            list1.add(i);
            list2.add(i);
        }
    }

    private void fillArraysWithRandom(final  CustomList<Integer> list1, List<Integer> list2 , int size) {
        for(int i = 0; i < size; i++) {
            int value = i * 10 + (int)(Math.random() * 100);
            list1.add(value);
            list2.add(value);
        }
    }

    private void addToCollection(CustomList<Integer> list1, List<Integer> list2, int[] arr) {
        for (Integer value: arr) {
            list1.add(value);
            list2.add(value);
        }
    }
}
