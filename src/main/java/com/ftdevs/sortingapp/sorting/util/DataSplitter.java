package com.ftdevs.sortingapp.sorting.util;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;

// Класс для разделения данных на части
public final class DataSplitter {

    private DataSplitter() {}

    public static <T> CustomList<CustomList<T>> divideList(
            final CustomList<T> list, final int threadCount) {
        final CustomList<CustomList<T>> chunks = new CustomArrayList<>();
        final int totalSize = list.size();
        final int baseChunkSize = totalSize / threadCount;
        final int remainder = totalSize % threadCount;

        int startIndex = 0;
        for (int i = 0; i < threadCount; i++) {
            final int chunkSize = baseChunkSize + (i < remainder ? 1 : 0);
            final int endIndex = startIndex + chunkSize;

            final CustomList<T> chunk = new CustomArrayList<>(chunkSize);
            for (int j = startIndex; j < endIndex; j++) {
                chunk.add(list.get(j));
            }

            chunks.add(chunk);
            startIndex = endIndex;
        }

        validateChunking(startIndex, totalSize);
        return chunks;
    }

    private static void validateChunking(final int processedElements, final int totalElements) {
        if (processedElements != totalElements) {
            throw new IllegalStateException(
                    String.format(
                            "Ошибка разбиения: обработано %d из %d элементов",
                            processedElements, totalElements));
        }
    }
}
