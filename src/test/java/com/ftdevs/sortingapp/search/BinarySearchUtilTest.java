package com.ftdevs.sortingapp.search;

import static org.junit.jupiter.api.Assertions.*;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import java.util.Comparator;
import org.junit.jupiter.api.Test;

final class BinarySearchUtilTest {

    private CustomList<Integer> createSortedIntegers() {
        final CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(7);
        list.add(9);
        return list;
    }

    private BinarySearchUtilTest() {}

    @Test
    void shouldFindExistingElement() {
        final CustomList<Integer> list = createSortedIntegers();
        final int idx = BinarySearchUtil.binarySearch(list, 5, Comparator.naturalOrder());
        assertEquals(2, idx, "Should return 2");
    }

    @Test
    void shouldReturnMinusOneForMissingElement() {
        final CustomList<Integer> list = createSortedIntegers();
        final int idx = BinarySearchUtil.binarySearch(list, 4, Comparator.naturalOrder());
        assertEquals(-1, idx, "Should return -1");
    }

    @Test
    void shouldReturnMinusOneForNullList() {
        final int idx = BinarySearchUtil.binarySearch(null, 5, Comparator.naturalOrder());
        assertEquals(-1, idx, "Should return -1");
    }

    @Test
    void shouldReturnMinusOneForNullComparator() {
        final CustomList<Integer> list = createSortedIntegers();
        final int idx = BinarySearchUtil.binarySearch(list, 5, null);
        assertEquals(-1, idx, "Should return -1");
    }

    @Test
    void shouldFindFirstElement() {
        final CustomList<Integer> list = createSortedIntegers();
        final int idx = BinarySearchUtil.binarySearch(list, 1, Comparator.naturalOrder());
        assertEquals(0, idx, "Should return 0");
    }

    @Test
    void shouldFindLastElement() {
        final CustomList<Integer> list = createSortedIntegers();
        final int idx = BinarySearchUtil.binarySearch(list, 9, Comparator.naturalOrder());
        assertEquals(4, idx, "Should return 4");
    }
}
