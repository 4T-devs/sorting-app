package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.*;

public class MultithreadSorting<T> implements ISortStrategy<T> {
    private ISortStrategy<T> sort;
    private Comparator<T> comparator;
    private int numThreads;


    public MultithreadSorting(ISortStrategy<T> sort) {
        this.sort = sort;
        this.numThreads = 2;
    }

    public void setSort(ISortStrategy<T> sort) {
        this.sort = sort;
    }

    public void setNumThreads(int numThreads) {
        this.numThreads = numThreads;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public ISortStrategy<T> getSortStrategy() {
        return sort;
    }

    private class ThreadSort implements Callable<CustomList<T>> {
        private final CustomList<T> threadList;


        public ThreadSort(CustomList<T> threadList) {
            this.threadList = threadList;
        }

        @Override
        public CustomList<T> call() {
            ISortStrategy<T> sort = MultithreadSorting.this.sort;
            Comparator<T> comparator = MultithreadSorting.this.comparator;

            sort.sort(threadList, comparator);
            return threadList;
        }
    }

    @Override
    public void sort(CustomList<T> list, Comparator<T> comparator) {
        this.comparator = comparator;
        System.out.println("Num of Threads = " + numThreads);
        if(list == null || list.size() <= 1 || comparator == null) {
            return;
        }

        CustomList<CustomList<T>> sortedLists = new CustomArrayList<>();
        try(ExecutorService executorService = Executors.newFixedThreadPool(numThreads);) {

            CustomList<CustomList<T>> lists = divideList(list);
            CustomList<Future<CustomList<T>>> futures = new CustomArrayList<>();

            //Добавление задач в ThreadPool
            for(int i = 0; i < numThreads; i++) {
                futures.add(executorService.submit(new ThreadSort(lists.get(i))));
            }

            //Ожидание пока все потоки выполнят сортировку
            for(int i = 0; i < numThreads; i++) {
                sortedLists.add(futures.get(i).get());
            }

            //Слияние отсортированных списков
            CustomList<T> result = mergeLists(sortedLists);

            //Копирование в исходный массив значений из отсортированного
            for(int i = 0; i < result.size(); i++) {
                list.set(i, result.get(i));
            }

        } catch (RuntimeException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private CustomList<CustomList<T>> divideList(CustomList<T> list) {
        final CustomList<CustomList<T>> lists = new CustomArrayList<>();
        final int baseChunkSize = getBaseChunkSize(list);
        final int remainingElements = list.size() % numThreads;

        int startIndex = 0;
        for (int i = 0; i < numThreads; i++) {
            final int chunkSize = baseChunkSize + (i < remainingElements ? 1 : 0);

            CustomList<T> threadList = new CustomArrayList<>(chunkSize);
            for(int j = 0; j < chunkSize; j++) {
                threadList.add(list.get(startIndex + j));
            }
            lists.add(threadList);
            startIndex += chunkSize;
        }
        return lists;
    }

    private CustomList<T> mergeLists(CustomList<CustomList<T>> sortedLists) {

        class Node {
            T value;
            int listId;
            int valuePos;

            public Node(T value, int listId, int valuePos) {
                this.value = value;
                this.listId = listId;
                this.valuePos = valuePos;
            }
        }

        Comparator<Node> nodeComparator = (node1, node2) -> comparator.compare(node1.value, node2.value);
        CustomList<T> result = new CustomArrayList<>();
        int numOfLists = sortedLists.size();
        PriorityQueue<Node> queue = new PriorityQueue<>(nodeComparator);

        for (int i = 0; i < numOfLists; i++) {
            T value = sortedLists.get(i).get(0);
            queue.add(new Node(value, i, 0));
        }

        while (!queue.isEmpty()) {
            Node minValue = queue.poll();
            result.add(minValue.value);
            int listId = minValue.listId;
            int listSize = sortedLists.get(listId).size();
            int newValuePos = minValue.valuePos < (listSize - 1) ? ++minValue.valuePos : -1;

            if(newValuePos < 0) {
                continue;
            }

            T newValue = sortedLists.get(listId).get(newValuePos);
            Node nextToQueue = new Node(newValue, listId, newValuePos);
            queue.add(nextToQueue);
        }
        return result;
    }

    private int getBaseChunkSize(CustomList<T> list) {
        int baseChunkSize = list.size() / numThreads;
        if(list.size() < numThreads) {
            setNumThreads(1);
            baseChunkSize = list.size();
        }

        return baseChunkSize;
    }
}

