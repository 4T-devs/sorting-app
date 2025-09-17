package com.ftdevs.sortingapp.search;

import java.util.Comparator;

public final class BinarySearchUtil {

    private BinarySearchUtil() {}

    /**
     * @param array отсортированный массив
     * @param key искомый элемент
     * @param comparator компаратор
     * @return индекс найденного элемента или -1, если не найден
     */
    public static <T> int binarySearch(
            final T[] array, final T key, final Comparator<? super T> comparator) {
        int result = -1;

        if (array != null && comparator != null) {
            int lowerLimit = 0;
            int upperLimit = array.length - 1;

            while (lowerLimit <= upperLimit) {
                final int mid = lowerLimit + ((upperLimit - lowerLimit) >>> 1);
                final int compareResult = comparator.compare(array[mid], key);

                if (compareResult == 0) {
                    result = mid;
                    break;
                } else if (compareResult < 0) {
                    lowerLimit = mid + 1;
                } else {
                    upperLimit = mid - 1;
                }
            }
        }

        return result;
    }
}
