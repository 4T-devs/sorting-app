package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomList;
import java.util.Comparator;

public class InsertionSorting<T extends Comparable<T>> implements ISortStrategy<T> {

    private final Comparator<T> comparator;

    public InsertionSorting() {
        this.comparator = null;
    }

    public InsertionSorting(final Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void sort(final CustomList<T> collection) {
        if (collection == null || collection.size() <= 1) {
            return;
        }

        for (int i = 1; i < collection.size(); i++) {
            final T key = collection.get(i);
            int idx = i - 1;

            while (idx >= 0 && compare(collection.get(idx), key) > 0) {
                collection.set(idx + 1, collection.get(idx));
                idx--;
            }

            collection.set(idx + 1, key);
        }
    }

    @SuppressWarnings("PMD.OnlyOneReturn")
    private int compare(final T first, final T second) {
        if (comparator != null) {
            return comparator.compare(first, second);
        } else if (first != null) {
            return first.compareTo(second);
        } else {
            throw new IllegalArgumentException(
                    "Objects don't implement Comparable and no comparator provided");
        }
    }
}
