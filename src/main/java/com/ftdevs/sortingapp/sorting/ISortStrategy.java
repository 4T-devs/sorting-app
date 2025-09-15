package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomList;
import java.util.Comparator;

@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface ISortStrategy<T> {
    void sort(CustomList<T> collection, Comparator<T> comparator);
}
