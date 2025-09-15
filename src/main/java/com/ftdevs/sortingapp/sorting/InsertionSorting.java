package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomList;
import java.util.Comparator;

public class InsertionSorting<T> implements ISortStrategy<T> {

    @Override
    public void sort(final CustomList<T> collection, final Comparator<T> comparator) {
        if (collection == null || collection.size() <= 1) {
            return;
        }

        for (int i = 1; i < collection.size(); i++) {
            final T key = collection.get(i);
            int idx = i - 1;

            while (idx >= 0 && comparator.compare(collection.get(idx), key) > 0) {
                collection.set(idx + 1, collection.get(idx));
                idx--;
            }

            collection.set(idx + 1, key);
        }
    }
}
