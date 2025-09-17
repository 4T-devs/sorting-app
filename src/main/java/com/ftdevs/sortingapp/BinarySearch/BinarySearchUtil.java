package com.ftdevs.sortingapp.BinarySearch;

import java.util.Comparator;

public final class BinarySearchUtil {
    private BinarySearchUtil() {}

    /**
     * @param array отсортированный массив
     * @param key искомый элемент
     * @param comparator компаратор
     * @return индекс найденного элемента или -1, если не найден
     */
    public static <T> int binarySearch(T[] array, T key, Comparator<? super T> comparator) {
        if (array == null || comparator == null) return -1;
        int lowerLimit = 0, upperLimit = array.length - 1;
        while (lowerLimit <= upperLimit) {
            int mid = lowerLimit + ((upperLimit - lowerLimit) >>> 1);
            int compareResult = comparator.compare(array[mid], key);
            if (compareResult == 0) return mid;
            if (compareResult < 0) lowerLimit = mid + 1;
            else upperLimit = mid - 1;
        }
        return -1;
    }
}

