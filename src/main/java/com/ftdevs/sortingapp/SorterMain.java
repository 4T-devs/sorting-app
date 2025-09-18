package com.ftdevs.sortingapp;

import java.util.Arrays;
import java.util.concurrent.*;

public final class SorterMain {
        private static final int THRESHOLD = 10;
        private static final int THREAD_POOL_SIZE = 4;
        private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        public static void main(String[] args) throws InterruptedException, ExecutionException {
            int[] array = {64, 34, 25, 12, 22, 11, 90, 88, 76, 50, 42, 33, 21, 19, 8, 5};

            System.out.println("Исходный массив: " + Arrays.toString(array));

            // Запускаем сортировку и ждем завершения
            quickSort(array).get();

            System.out.println("Отсортированный массив: " + Arrays.toString(array));

            executor.shutdown();
        }

        public static Future<Void> quickSort(int[] array) {
            return quickSort(array, 0, array.length - 1);
        }

        private static Future<Void> quickSort(int[] array, int low, int high) {
            System.out.println(Thread.currentThread().getName());
            if (low >= high) {
                return CompletableFuture.completedFuture(null);
            }

            int pivotIndex = partition(array, low, high);

            // Создаем задачи для левой и правой частей
            Future<Void> leftFuture = executor.submit(() -> {
                quickSort(array, low, pivotIndex - 1).get();
                return null;
            });

            Future<Void> rightFuture = executor.submit(() -> {
                quickSort(array, pivotIndex + 1, high).get();
                return null;
            });

            // Ожидаем завершения обеих задач
            return CompletableFuture.runAsync(() -> {
                try {
                    leftFuture.get();
                    rightFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Ошибка при выполнении сортировки", e);
                }
            }, executor);
        }

        private static int partition(int[] array, int low, int high) {
            int pivot = array[high];
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (array[j] <= pivot) {
                    i++;
                    swap(array, i, j);
                }
            }

            swap(array, i + 1, high);
            return i + 1;
        }

        private static void swap(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }