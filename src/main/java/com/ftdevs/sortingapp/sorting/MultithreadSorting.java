package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class MultithreadSorting<T> implements ISortStrategy<T> {
    private static final Logger LOGGER = Logger.getLogger(MultithreadSorting.class.getName());

    private ISortStrategy<T> sortStrategy;
    private Comparator<T> comparator;
    private int numThreads;

    private static final int MIN_MULTI_SIZE = 1000;
    private static final int MIN_CHUNK_SIZE = 250;

    public MultithreadSorting(final ISortStrategy<T> sortStrategy) {
        this.sortStrategy = sortStrategy;
        this.numThreads = calculateThreads();
    }

    public void setNumThreads(final int numThreads) {
        this.numThreads = numThreads <= 0 ? 1 : numThreads;
    }

    @Override
    @SuppressWarnings("PMD.AvoidPrintStackTrace")
    public void sort(final CustomList<T> list, final Comparator<T> comparator) {
        this.comparator = comparator;

        if (list == null || list.size() <= 1 || comparator == null) {
            return;
        }

        if (shouldUseSingleThread(list)) {
            sortStrategy.sort(list, comparator);
        } else {
            performMultithreadSort(list, comparator);
        }
    }

    private boolean shouldUseSingleThread(final CustomList<T> list) {
        final boolean useSingleThread;
        if (list.size() < MIN_MULTI_SIZE) {
            useSingleThread = true;
        } else if (numThreads <= 1) {
            useSingleThread = true;
        } else {
            final int optimalThreads = calculateOptimalThreadCount(list.size());
            final int chunkSize = list.size() / optimalThreads;
            useSingleThread = chunkSize < MIN_CHUNK_SIZE;
        }
        return useSingleThread;
    }

    private int calculateOptimalThreadCount(final int listSize) {
        final int baseThreads = numThreads;
        final int maxThreads = Math.min(baseThreads, 8);
        final int threadsBaseOnSize = listSize / MIN_CHUNK_SIZE;
        return Math.min(maxThreads, Math.max(1, threadsBaseOnSize));
    }

    private void performMultithreadSort(final CustomList<T> list, final Comparator<T> comparator) {
        final int numThreads = calculateOptimalThreadCount(list.size());

        System.out.printf(
                "Многопоточная сортировка: элементов=%d, потоков=%d%n", list.size(), numThreads);
        final CustomList<CustomList<T>> sortedLists = new CustomArrayList<>();

        try (ExecutorService executorService = createExecutorService(numThreads)) {

            final CustomList<CustomList<T>> chunks = divideList(list, numThreads);
            final CustomList<Future<CustomList<T>>> futures = new CustomArrayList<>();

            // Добавление задач в ThreadPool
            for (int i = 0; i < numThreads; i++) {
                final int chunkIndex = i;
                futures.add(
                        executorService.submit(
                                () -> {
                                    final CustomList<T> chunk = chunks.get(chunkIndex);
                                    sortStrategy.sort(chunk, comparator);
                                    return chunk;
                                }));
            }

            // Ожидание пока все потоки выполнят сортировку
            collectSortedResults(futures, sortedLists, numThreads);

            // Слияние отсортированных списков
            final CustomList<T> result = mergeSortedChunks(sortedLists);

            // Копирование в исходный массив значений из отсортированного
            copyResult(result, list);

        } catch (ExecutionException e) {
            LOGGER.log(Level.SEVERE, "Ошибка выполнения многопоточной сортировки", e);
            sortStrategy.sort(list, comparator);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Многопоточная сортировка прервана", e);
            Thread.currentThread().interrupt();
            sortStrategy.sort(list, comparator);
        }
    }

    private void collectSortedResults(
            final CustomList<Future<CustomList<T>>> futures,
            final CustomList<CustomList<T>> sortedLists,
            final int optimalThreads)
            throws InterruptedException, ExecutionException {
        for (int i = 0; i < optimalThreads; i++) {
            try {
                sortedLists.add(futures.get(i).get());
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Поток прерван при ожидании результата", e);
                Thread.currentThread().interrupt();
                throw e;
            } catch (ExecutionException e) {
                LOGGER.log(Level.SEVERE, "Ошибка выполнения задачи сортировки", e);
                throw e;
            }
        }
    }

    private ExecutorService createExecutorService(final int numThreads) {
        final ThreadFactory threadFactory =
                new ThreadFactory() {
                    private int threadCount = 0;

                    @Override
                    public Thread newThread(final @NonNull Runnable runnable) {
                        final Thread thread =
                                new Thread(runnable, "MultiSort-Worker-" + (++threadCount));
                        thread.setDaemon(false);
                        thread.setPriority(Thread.NORM_PRIORITY);
                        return thread;
                    }
                };

        return Executors.newFixedThreadPool(numThreads, threadFactory);
    }

    private CustomList<CustomList<T>> divideList(final CustomList<T> list, final int numThreads) {
        final CustomList<CustomList<T>> chunks = new CustomArrayList<>();
        final int baseChunkSize = list.size() / numThreads;
        final int remainingElements = list.size() % numThreads;

        int startIndex = 0;
        for (int i = 0; i < numThreads; i++) {
            final int chunkSize = baseChunkSize + (i < remainingElements ? 1 : 0);
            final int endIndex = startIndex + chunkSize;

            final CustomList<T> chunk = new CustomArrayList<>(chunkSize);
            for (int j = startIndex; j < endIndex; j++) {
                chunk.add(list.get(j));
            }

            chunks.add(chunk);
            startIndex = endIndex;
        }

        return chunks;
    }

    @SuppressWarnings("PMD.ShortClassName")
    private CustomList<T> mergeSortedChunks(final CustomList<CustomList<T>> sortedLists) {

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

    private void copyResult(final CustomList<T> source, final CustomList<T> destination) {
        for (int i = 0; i < source.size(); i++) {
            destination.set(i, source.get(i));
        }
    }

    private int calculateThreads() {
        return Runtime.getRuntime().availableProcessors();
    }
}
