package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;

import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelSorting<T> implements ISortStrategy<T> {
    private final ISortStrategy<T> sortStrategy;
    private final int threshold;

    public ParallelSorting(ISortStrategy<T> sortStrategy, int threshold) {
        this.sortStrategy = sortStrategy;
        this.threshold = threshold;
    }

    public ParallelSorting(ISortStrategy<T> sortStrategy) {
        this(sortStrategy, 10);
    }

    @Override
    public void sort(CustomList<T> collection, Comparator<T> comparator) {
        try(ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            if(collection == null || collection.size() <= 1 || comparator == null) {
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

        public SortTask(CustomList<T> list, Comparator<T> comparator, int left, int right) {
            this.list = list;
            this.comparator = comparator;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            CustomList<T> result = new CustomArrayList<>();
            int size = right - left + 1;
            if(size <= threshold) {
                for (int i = left; i <= right; i++) {
                    result.add(list.get(i));
                }
                sortStrategy.sort(result, comparator);

                int idx = 0;
                for(int i = left; i <= right; i++) {
                    list.set(i, result.get(idx++));
                }

            } else {
                int mid = (left + right) / 2;
                SortTask leftTask = new SortTask(list, comparator, left, mid);
                SortTask rightTask = new SortTask(list, comparator, mid + 1, right);
                invokeAll(leftTask, rightTask);
                mergeLists(list, left, mid, right);
            }
        }

        private void mergeLists(CustomList<T> list, int left, int mid, int right) {
            CustomList<T> tempList = new CustomArrayList<>(right - left + 1);

            int i = left;
            int j = mid + 1;
            while (i <= mid && j <= right) {
                if(comparator.compare(list.get(i), list.get(j)) < 0) {
                    tempList.add(list.get(i++));
                } else {
                    tempList.add(list.get(j++));
                }
            }

            while (i <= mid) {
                tempList.add(list.get(i++));
            }

            while (j <= right) {
                tempList.add(list.get(j++));
            }

            int idx = 0;
            for(int k = left; k <= right; k++) {
                list.set(k, tempList.get(idx++));
            }
        }
    }

    public static void main(String[] args) {
        ParallelSorting<Integer> parallelSorting = new ParallelSorting<>(new InsertionSorting<>());
        CustomList<Integer> listToSort = new CustomArrayList<>();

//        for(int i = 100; i > 0; i--) {
//            listToSort.add(i);
//        }
//
//        for(int i = 0; i < 100; i++) {
//            listToSort.add(i);
//        }
        parallelSorting.sort(listToSort, Integer::compareTo);

        for(int i = 0; i < listToSort.size(); i++) {
            System.out.println(listToSort.get(i));
        }
    }
}
