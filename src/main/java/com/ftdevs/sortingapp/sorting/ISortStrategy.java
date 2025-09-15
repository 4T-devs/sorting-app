package com.ftdevs.sortingapp.sorting;

import com.ftdevs.sortingapp.collections.CustomList;

@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface ISortStrategy<T extends Comparable<T>> {
    void sort(CustomList<T> collection);
}
