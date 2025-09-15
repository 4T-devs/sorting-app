package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomList;

@SuppressWarnings("PMD.AtLeastOneConstructor")
public class InsertionSorting<T extends Comparable<T>> implements ISortStrategy<T> {

    @Override
    public void sort(final CustomList<T> collection) {
        if (collection == null || collection.size() <= 1) {
            return;
        }

        for (int i = 1; i < collection.size(); i++) {
            final T key = collection.get(i);
            int idx = i - 1;

            while (idx >= 0 && collection.get(idx).compareTo(key) > 0) {
                collection.set(idx + 1, collection.get(idx));
                idx--;
            }

            collection.set(idx + 1, key);
        }
    }
}
