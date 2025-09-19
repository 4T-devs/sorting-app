package com.ftdevs.sortingapp.counter;

import com.ftdevs.sortingapp.collections.CustomList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public final class ConcurrentCounter {

    private ConcurrentCounter() {}

    public static <T> int countOccurrences(
            final CustomList<T> list, final T target, final int nThreads) {
        final int size = list.size();

        // Ограничиваем число потоков доступными ядрами
        final int threads = Math.min(nThreads, Runtime.getRuntime().availableProcessors());

        // Размер чанка
        final int chunkSize = (int) Math.ceil((double) size / threads);

        final ExecutorService executor = Executors.newFixedThreadPool(threads);
        final List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < size; i += chunkSize) {
            final int start = i;
            final int end = Math.min(i + chunkSize, size);

            final Callable<Integer> task =
                    () -> {
                        int localCount = 0;
                        for (int j = start; j < end; j++) {
                            if (target.equals(list.get(j))) {
                                localCount++;
                            }
                        }
                        return localCount;
                    };

            futures.add(executor.submit(task));
        }

        int totalCount = 0;
        for (final Future<Integer> future : futures) {
            try {
                totalCount += future.get();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CounterException("Thread interrupted", e);
            } catch (ExecutionException e) {
                throw new CounterException("Task execution failed", e);
            }
        }
        executor.shutdown();
        return totalCount;
    }

    public static class CounterException extends RuntimeException {
        public CounterException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}
