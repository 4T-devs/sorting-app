package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.sorting.strategy.ISortStrategy;
import com.ftdevs.sortingapp.sorting.support.ChunkMerger;
import com.ftdevs.sortingapp.sorting.util.DataCopier;
import com.ftdevs.sortingapp.sorting.util.DataSplitter;
import java.util.Comparator;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.NonNull;

public final class MultithreadSorting<T> implements ISortStrategy<T> {
    private static final Logger LOGGER = Logger.getLogger(MultithreadSorting.class.getName());

    // Конфиги
    private static final int MIN_MULTI_SIZE = 1000;
    private static final int MIN_CHUNK_SIZE = 250;
    private static final int MAX_THREADS = 8;

    // Иммутабельные поля
    private final ISortStrategy<T> sortStrategy;
    private final int numThreads;
    private final TaskExecutor<T> taskExecutor;
    private final ChunkMerger<T> chunkMerger;

    // Стандартный конструктор с определением максимального количества потоков
    public MultithreadSorting(final ISortStrategy<T> sortStrategy) {
        this(sortStrategy, Runtime.getRuntime().availableProcessors());
    }

    // Конструктор с явным указанием количества потоков
    public MultithreadSorting(final ISortStrategy<T> sortStrategy, final int numThreads) {
        if (sortStrategy == null) {
            throw new IllegalArgumentException("SortStrategy не может быть null");
        }

        this.sortStrategy = sortStrategy;
        this.numThreads = ThreadCalculator.validateAndNormalizeThreadCount(numThreads);
        this.taskExecutor = new TaskExecutor<>(sortStrategy);
        this.chunkMerger = new ChunkMerger<>();

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Создан MultithreadSorting с {0} потоками", this.numThreads);
        }
    }

    // Фабричный метод для создания экземпляра с оптимальным количеством потоков для данного размера
    public static <T> MultithreadSorting<T> createOptimal(
            final ISortStrategy<T> sortStrategy, final int expectedDataSize) {
        final int optimalThreads = ThreadCalculator.calculateOptimalThreads(expectedDataSize);
        return new MultithreadSorting<>(sortStrategy, optimalThreads);
    }

    @Override
    public void sort(final CustomList<T> list, final Comparator<T> comparator) {
        if (list == null || list.size() <= 1 || comparator == null) {
            return;
        }

        if (shouldUseSingleThread(list)) {
            performSingleThreadSort(list, comparator);
        } else {
            performMultithreadSort(list, comparator);
        }
    }

    private boolean shouldUseSingleThread(final CustomList<T> list) {
        final boolean useSingleThread;
        if (list.size() < MIN_MULTI_SIZE || numThreads <= 1) {
            useSingleThread = true;
        } else {
            final int optimalThreads = ThreadCalculator.calculateOptimalThreads(list.size());
            final int chunkSize = list.size() / optimalThreads;
            useSingleThread = chunkSize < MIN_CHUNK_SIZE;
        }
        return useSingleThread;
    }

    private void performSingleThreadSort(final CustomList<T> list, final Comparator<T> comparator) {
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Однопоточная сортировка для {0} элементов", list.size());
        }
        sortStrategy.sort(list, comparator);
    }

    private void performMultithreadSort(final CustomList<T> list, final Comparator<T> comparator) {
        final int optimalThreads = ThreadCalculator.calculateOptimalThreads(list.size());

        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(
                    Level.INFO,
                    "Многопоточная сортировка: элементов={0}, потоков={1}",
                    new Object[] {list.size(), optimalThreads});
        }

        try {
            final CustomList<CustomList<T>> chunks = DataSplitter.divideList(list, optimalThreads);
            final CustomList<CustomList<T>> sortedChunks =
                    taskExecutor.executeSortingTasks(chunks, comparator, optimalThreads);
            final CustomList<T> mergedResult =
                    chunkMerger.mergeSortedChunks(sortedChunks, comparator);

            DataCopier.copyResult(mergedResult, list);

        } catch (ExecutionException e) {
            LOGGER.log(Level.SEVERE, "Ошибка выполнения многопоточной сортировки", e);
            fallbackToSingleThreadSort(list, comparator);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Многопоточная сортировка прервана", e);
            Thread.currentThread().interrupt();
            fallbackToSingleThreadSort(list, comparator);
        }
    }

    private void fallbackToSingleThreadSort(
            final CustomList<T> list, final Comparator<T> comparator) {
        if (LOGGER.isLoggable(Level.INFO)) {
            LOGGER.log(Level.INFO, "Fallback на однопоточную сортировку");
        }
        sortStrategy.sort(list, comparator);
    }

    // Класс для расчета оптимального количества потоков
    /* default */ static final class ThreadCalculator {
        private ThreadCalculator() {}

        /* default */ static int calculateOptimalThreads(final int dataSize) {
            final int threadCount;
            if (dataSize < MIN_MULTI_SIZE) {
                threadCount = 1;
            } else {
                final int availableProc = Runtime.getRuntime().availableProcessors();
                final int threadsOnData = dataSize / MIN_CHUNK_SIZE;
                threadCount = Math.min(availableProc, Math.min(MAX_THREADS, threadsOnData));
            }

            return threadCount;
        }

        /* default */ static int validateAndNormalizeThreadCount(final int requestedThreads) {
            final int threadCount;
            if (requestedThreads <= 0) {
                threadCount = 1;
            } else {
                threadCount = Math.min(requestedThreads, MAX_THREADS);
            }
            return threadCount;
        }
    }

    // Класс для выполнения задач сортировки в потоках
    /* default */ static final class TaskExecutor<T> {
        private final ISortStrategy<T> sortStrategy;

        /* default */ TaskExecutor(final ISortStrategy<T> sortStrategy) {
            this.sortStrategy = sortStrategy;
        }

        /* default */ CustomList<CustomList<T>> executeSortingTasks(
                final CustomList<CustomList<T>> chunks,
                final Comparator<T> comparator,
                final int threadCount)
                throws InterruptedException, ExecutionException {

            try (ExecutorService executorService = createExecutorService(threadCount)) {
                final CustomList<Future<CustomList<T>>> futures =
                        submitSortingTasks(chunks, comparator, executorService);
                return collectSortedResults(futures);
            }
        }

        private ExecutorService createExecutorService(final int threadCount) {
            final ThreadFactory threadFactory = new SortingThreadFactory();
            return Executors.newFixedThreadPool(threadCount, threadFactory);
        }

        private CustomList<Future<CustomList<T>>> submitSortingTasks(
                final CustomList<CustomList<T>> chunks,
                final Comparator<T> comparator,
                final ExecutorService executorService) {

            final CustomList<Future<CustomList<T>>> futures = new CustomArrayList<>();

            for (int i = 0; i < chunks.size(); i++) {
                final int chunkIndex = i;
                futures.add(
                        executorService.submit(
                                () -> {
                                    final CustomList<T> chunk = chunks.get(chunkIndex);
                                    sortStrategy.sort(chunk, comparator);
                                    return chunk;
                                }));
            }

            return futures;
        }

        private CustomList<CustomList<T>> collectSortedResults(
                final CustomList<Future<CustomList<T>>> futures)
                throws InterruptedException, ExecutionException {

            final CustomList<CustomList<T>> sortedLists = new CustomArrayList<>();

            for (int i = 0; i < futures.size(); i++) {
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

            return sortedLists;
        }
    }

    // ThreadFactory для создания именованных потоков
    private static final class SortingThreadFactory implements ThreadFactory {
        private int threadNumber = 0;

        @Override
        public Thread newThread(final @NonNull Runnable runnable) {
            final Thread thread = new Thread(runnable, "MultiSort-Worker-" + (++threadNumber));
            thread.setDaemon(false);
            thread.setPriority(Thread.NORM_PRIORITY);
            return thread;
        }
    }
}
