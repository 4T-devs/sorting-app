package com.ftdevs.sortingapp.sorting.support;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import java.util.Comparator;
import java.util.Queue;
import lombok.Getter;

// Исполнитель k-way merge алгоритма
final class KWayMergeExecutor<T> {
    private final CustomList<CustomList<T>> sortedLists;
    private final Comparator<T> comparator;

    /* default */ KWayMergeExecutor(
            final CustomList<CustomList<T>> sortedLists, final Comparator<T> comparator) {
        this.sortedLists = sortedLists;
        this.comparator = comparator;
    }

    /* default */ CustomList<T> execute() {
        final CustomList<T> result = new CustomArrayList<>();
        final Queue<ChunkElement> priorityQueue = new java.util.PriorityQueue<>();
        final int[] chunkPointers = new int[sortedLists.size()];

        initializePriorityQueue(priorityQueue, chunkPointers);

        while (!priorityQueue.isEmpty()) {
            final ChunkElement minElement = priorityQueue.poll();
            result.add(minElement.getValue());

            addNextElementFromSameChunk(priorityQueue, chunkPointers, minElement);
        }

        return result;
    }

    private void initializePriorityQueue(
            final Queue<ChunkElement> priorityQueue, final int... chunkPointers) {
        for (int i = 0; i < sortedLists.size(); i++) {
            final CustomList<T> chunk = sortedLists.get(i);
            priorityQueue.offer(new ChunkElement(chunk.get(0), i, 0));
            chunkPointers[i] = 0;
        }
    }

    private void addNextElementFromSameChunk(
            final Queue<ChunkElement> priorityQueue,
            final int[] chunkPointers,
            final ChunkElement minElement) {
        final int nextIndex = chunkPointers[minElement.chunkIndex] + 1;
        final CustomList<T> sourceChunk = sortedLists.get(minElement.chunkIndex);

        if (nextIndex < sourceChunk.size()) {
            final T nextValue = sourceChunk.get(nextIndex);
            priorityQueue.offer(new ChunkElement(nextValue, minElement.chunkIndex, nextIndex));
            chunkPointers[minElement.chunkIndex] = nextIndex;
        }
    }

    // Элемент chunk'а для priority queue
    @Getter
    private final class ChunkElement implements Comparable<ChunkElement> {
        /* default */ final T value;
        /* default */ final int chunkIndex;
        /* default */ final int elementIndex;

        /* default */ ChunkElement(final T value, final int chunkIndex, final int elementIndex) {
            this.value = value;
            this.chunkIndex = chunkIndex;
            this.elementIndex = elementIndex;
        }

        @Override
        public int compareTo(final ChunkElement other) {
            return comparator.compare(this.value, other.value);
        }
    }
}
