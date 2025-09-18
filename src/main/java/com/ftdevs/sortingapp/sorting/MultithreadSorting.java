package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.*;

public class MultithreadSorting<T> implements ISortStrategy<T> {
    private ISortStrategy<T> sort;
    private Comparator<T> comparator;
    private int numThreads;

    public MultithreadSorting(final ISortStrategy<T> sort) {
        this.sort = sort;
        this.numThreads = 2;
    }

    public void setSortStrategy(final ISortStrategy<T> sort) {
        this.sort = sort;
    }

    public void setNumThreads(final int numThreads) {
        this.numThreads = numThreads <= 0 ? 1 : numThreads;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public ISortStrategy<T> getSortStrategy() {
        return sort;
    }

    @Override
    @SuppressWarnings("PMD.AvoidPrintStackTrace")
    public void sort(final CustomList<T> list, final Comparator<T> comparator) {
        this.comparator = comparator;

        if (list == null || list.size() <= 1 || comparator == null) {
            return;
        }

        final CustomList<CustomList<T>> sortedLists = new CustomArrayList<>();
        try (ExecutorService executorService = Executors.newFixedThreadPool(numThreads); ) {

            final CustomList<CustomList<T>> lists = divideList(list);
            final CustomList<Future<CustomList<T>>> futures = new CustomArrayList<>();

            // Добавление задач в ThreadPool
            for (int i = 0; i < numThreads; i++) {
                final int finalI = i;
                futures.add(
                        executorService.submit(
                                () -> {
                                    sort.sort(lists.get(finalI), comparator);
                                    return lists.get(finalI);
                                }));
            }

            // Ожидание пока все потоки выполнят сортировку
            for (int i = 0; i < numThreads; i++) {
                sortedLists.add(futures.get(i).get());
            }

            // Слияние отсортированных списков
            final CustomList<T> result = mergeLists(sortedLists);

            // Копирование в исходный массив значений из отсортированного
            for (int i = 0; i < result.size(); i++) {
                list.set(i, result.get(i));
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private CustomList<CustomList<T>> divideList(final CustomList<T> list) {
        final CustomList<CustomList<T>> lists = new CustomArrayList<>();
        final int baseChunkSize = getBaseChunkSize(list);
        final int remainingElements = list.size() % numThreads;

        int startIndex = 0;
        for (int i = 0; i < numThreads; i++) {
            final int chunkSize = baseChunkSize + (i < remainingElements ? 1 : 0);

            final CustomList<T> threadList = new CustomArrayList<>(chunkSize);
            for (int j = 0; j < chunkSize; j++) {
                threadList.add(list.get(startIndex + j));
            }
            lists.add(threadList);
            startIndex += chunkSize;
        }
        return lists;
    }

    @SuppressWarnings("PMD.ShortClassName")
    private CustomList<T> mergeLists(final CustomList<CustomList<T>> sortedLists) {

        class Node {
            /* default */ final T value;
            /* default */ final int listId;
            /* default */ int valuePos;

            public Node(final T value, final int listId, final int valuePos) {
                this.value = value;
                this.listId = listId;
                this.valuePos = valuePos;
            }
        }

        final Comparator<Node> nodeComparator =
                (node1, node2) -> comparator.compare(node1.value, node2.value);
        final CustomList<T> result = new CustomArrayList<>();
        final int numOfLists = sortedLists.size();
        final Queue<Node> queue = new PriorityQueue<>(nodeComparator);

        for (int i = 0; i < numOfLists; i++) {
            final T value = sortedLists.get(i).get(0);
            queue.add(new Node(value, i, 0));
        }

        while (!queue.isEmpty()) {
            final Node minValue = queue.poll();
            result.add(minValue.value);
            final int listId = minValue.listId;
            final int listSize = sortedLists.get(listId).size();
            final int newValuePos = minValue.valuePos < (listSize - 1) ? ++minValue.valuePos : -1;

            if (newValuePos < 0) {
                continue;
            }

            final T newValue = sortedLists.get(listId).get(newValuePos);
            final Node nextToQueue = new Node(newValue, listId, newValuePos);
            queue.add(nextToQueue);
        }
        return result;
    }

    private int getBaseChunkSize(final CustomList<T> list) {
        int baseChunkSize = list.size() / numThreads;
        if (list.size() < numThreads) {
            setNumThreads(1);
            baseChunkSize = list.size();
        }
        return baseChunkSize;
    }
}
