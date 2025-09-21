package com.ftdevs.sortingapp.ui.context;

import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.sorting.MultithreadSorting;
import com.ftdevs.sortingapp.sorting.strategy.ISortStrategy;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Setter;

@Setter
public class SortingContext<T> {
    private ISortStrategy<T> strategy;
    private MultithreadSorting<T> multithreadSort;

    public void sort(CustomList<T> list, Comparator<T> comparator) {
        final Logger log = Logger.getLogger(SortingContext.class.getName());
        if (strategy == null) {
            throw new IllegalStateException("Стратегия сортировки не установлена");
        }

        long startTime = System.currentTimeMillis();
        multithreadSort = MultithreadSorting.createOptimal(strategy, list.size());
        multithreadSort.sort(list, comparator);
        long endTime = System.currentTimeMillis();
        if (log.isLoggable(Level.INFO)) {
            log.info(String.format("Сортировка выполнена за %d мс", endTime - startTime));
        }
    }
}
