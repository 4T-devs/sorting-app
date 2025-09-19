package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomList;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

@SuppressWarnings("PMD.AtLeastOneConstructor")
public class QuickSort<T> implements ISortStrategy<T> {

    @Override
    public void sort(final CustomList<T> list, final Comparator<T> comparator) {
        if (list == null || list.size() <= 1) {
            return;
        }

        final int cores = Runtime.getRuntime().availableProcessors();

        try (ForkJoinPool pool = new ForkJoinPool(cores)) {
            pool.invoke(new QuickSorter<>(list, 0, list.size() - 1, comparator));
        }
    }

    private static class QuickSorter<T> extends RecursiveAction {
        private final CustomList<T> list;
        private final int low;
        private final int high;
        private final Comparator<T> comparator;
        private static final int THRESHOLD = 100;

        public QuickSorter(
                final CustomList<T> list,
                final int low,
                final int high,
                final Comparator<T> comparator) {
            super();
            this.list = list;
            this.low = low;
            this.high = high;
            this.comparator = comparator;
        }

        @Override
        protected void compute() {
            if (low < high) {
                if (high - low <= THRESHOLD) {
                    iterativeQuickSort(low, high);
                } else {
                    final int pivotIndex = partition(low, high);

                    final QuickSorter<T> left =
                            new QuickSorter<>(list, low, pivotIndex - 1, comparator);
                    final QuickSorter<T> right =
                            new QuickSorter<>(list, pivotIndex + 1, high, comparator);

                    invokeAll(left, right);
                }
            }
        }

        private void iterativeQuickSort(final int low, final int high) {
            final Deque<Integer> stack = new ArrayDeque<>();
            stack.push(low);
            stack.push(high);

            while (!stack.isEmpty()) {
                final int currentHigh = stack.pop();
                final int currentLow = stack.pop();

                if (currentLow >= currentHigh) {
                    continue;
                }

                final int pivotIndex = partition(currentLow, currentHigh);

                if (pivotIndex - 1 > currentLow) {
                    stack.push(currentLow);
                    stack.push(pivotIndex - 1);
                }

                if (pivotIndex + 1 < currentHigh) {
                    stack.push(pivotIndex + 1);
                    stack.push(currentHigh);
                }
            }
        }

        private int partition(final int low, final int high) {
            final int middle = low + (high - low) / 2;
            final int pivotIndex = medianOfThree(low, middle, high);
            swap(pivotIndex, high);
            final T pivot = list.get(high);
            int index = low;
            for (int j = low; j < high; j++) {
                if (comparator.compare(list.get(j), pivot) < 0) {
                    swap(index, j);
                    index++;
                }
            }

            swap(index, high);
            return index;
        }

        private int medianOfThree(final int low, final int middle, final int high) {
            final T leftValue = list.get(low);
            final T middleValue = list.get(middle);
            final T rightValue = list.get(high);
            final int result;
            if (comparator.compare(leftValue, middleValue) < 0) {
                if (comparator.compare(middleValue, rightValue) < 0) {
                    result = middle; // leftValue < middleValue < rightValue
                } else if (comparator.compare(leftValue, rightValue) < 0) {
                    result = high; // leftValue < rightValue <= middleValue
                } else {
                    result = low; // rightValue <= leftValue < middleValue
                }
            } else {
                if (comparator.compare(leftValue, rightValue) < 0) {
                    result = low; // middleValue <= leftValue < rightValue
                } else if (comparator.compare(middleValue, rightValue) < 0) {
                    result = high; // middleValue < rightValue <= leftValue
                } else {
                    result = middle; // rightValue <= middleValue <= leftValue
                }
            }

            return result;
        }

        private void swap(final int firstIndex, final int secondIndex) {
            final T temp = list.get(firstIndex);
            list.set(firstIndex, list.get(secondIndex));
            list.set(secondIndex, temp);
        }
    }
}
