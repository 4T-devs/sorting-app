package com.ftdevs.sortingapp.search;

import com.ftdevs.sortingapp.collections.CustomList;
import java.util.Comparator;

public final class BinarySearchUtil {

    private BinarySearchUtil() {}

    /**
     * @param list отсортированный массив
     * @param key искомый элемент
     * @param comparator компаратор
     * @return индекс найденного элемента или -1, если не найден
     */
    public static <T> int binarySearch(
            final CustomList<T> list, final T key, final Comparator<? super T> comparator) {
        int result = -1;

        if (list != null && comparator != null) {
            int lowerLimit = 0;
            int upperLimit = list.size() - 1;

            while (lowerLimit <= upperLimit) {
                final int mid = lowerLimit + ((upperLimit - lowerLimit) >>> 1);
                final T midElem = list.get(mid);
                final int compareResult = comparator.compare(midElem, key);

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
