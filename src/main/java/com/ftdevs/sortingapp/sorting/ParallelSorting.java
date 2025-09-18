package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelSorting<T> implements ISortStrategy<T> {
    private final ISortStrategy<T> sortStrategy;
    private final int threshold;

    public ParallelSorting(final ISortStrategy<T> sortStrategy, final int threshold) {
        this.sortStrategy = sortStrategy;
        this.threshold = threshold;
    }

    public ParallelSorting(final ISortStrategy<T> sortStrategy) {
        this(sortStrategy, 10);
    }

    @Override
    public void sort(final CustomList<T> collection, final Comparator<T> comparator) {
        try (ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            if (collection == null || collection.size() <= 1 || comparator == null) {
                return;
            }
            forkJoinPool.invoke(new SortTask(collection, comparator, 0, collection.size() - 1));
        }
    }

    private class SortTask extends RecursiveAction {
        private final CustomList<T> list;
        private final Comparator<T> comparator;
        private final int left;
        private final int right;

        public SortTask(
                final CustomList<T> list,
                final Comparator<T> comparator,
                final int left,
                final int right) {
            super();
            this.list = list;
            this.comparator = comparator;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            final CustomList<T> result = new CustomArrayList<>();
            final int size = right - left + 1;
            if (size <= threshold) {
                for (int i = left; i <= right; i++) {
                    result.add(list.get(i));
                }
                sortStrategy.sort(result, comparator);

                int idx = 0;
                for (int i = left; i <= right; i++) {
                    list.set(i, result.get(idx++));
                }

            } else {
                final int mid = (left + right) / 2;
                final SortTask leftTask = new SortTask(list, comparator, left, mid);
                final SortTask rightTask = new SortTask(list, comparator, mid + 1, right);
                invokeAll(leftTask, rightTask);
                mergeLists(list, left, mid, right);
            }
        }

        private void mergeLists(
                final CustomList<T> list, final int left, final int mid, final int right) {
            final CustomList<T> tempList = new CustomArrayList<>(right - left + 1);

            int leftArrIndex = left;
            int rightArrIndex = mid + 1;
            while (leftArrIndex <= mid && rightArrIndex <= right) {
                if (comparator.compare(list.get(leftArrIndex), list.get(rightArrIndex)) < 0) {
                    tempList.add(list.get(leftArrIndex++));
                } else {
                    tempList.add(list.get(rightArrIndex++));
                }
            }

            while (leftArrIndex <= mid) {
                tempList.add(list.get(leftArrIndex++));
            }

            while (rightArrIndex <= right) {
                tempList.add(list.get(rightArrIndex++));
            }

            int idx = 0;
            for (int k = left; k <= right; k++) {
                list.set(k, tempList.get(idx++));
            }
        }
    }
}
