package com.ftdevs.sortingapp.sorting.support;

import com.ftdevs.sortingapp.collections.CustomList;
import java.util.Comparator;

// Класс слияния отсортированных частей коллекции
@SuppressWarnings("PMD.AtLeastOneConstructor")
public final class ChunkMerger<T> {

    public CustomList<T> mergeSortedChunks(
            final CustomList<CustomList<T>> sortedLists, final Comparator<T> comparator) {
        validateChunks(sortedLists);
        return performKWayMerge(sortedLists, comparator);
    }

    private void validateChunks(final CustomList<CustomList<T>> sortedLists) {
        for (int i = 0; i < sortedLists.size(); i++) {
            if (sortedLists.get(i).isEmpty()) {
                throw new IllegalStateException("Пустой чанк после сортировки: " + i);
            }
        }
    }

    private CustomList<T> performKWayMerge(
            final CustomList<CustomList<T>> sortedLists, final Comparator<T> comparator) {

        return new KWayMergeExecutor<>(sortedLists, comparator).execute();
    }
}
