package com.ftdevs.sortingapp.sorting.util;

import com.ftdevs.sortingapp.collections.CustomList;

// Класс для копирования результатов
public final class DataCopier {

    private DataCopier() {}

    public static <T> void copyResult(final CustomList<T> source, final CustomList<T> destination) {
        validateSizes(source, destination);

        for (int i = 0; i < source.size(); i++) {
            destination.set(i, source.get(i));
        }
    }

    private static <T> void validateSizes(
            final CustomList<T> source, final CustomList<T> destination) {
        if (source.size() != destination.size()) {
            throw new IllegalStateException(
                    String.format(
                            "Размеры не совпадают: source=%d, destination=%d",
                            source.size(), destination.size()));
        }
    }
}
