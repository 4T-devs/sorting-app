package com.ftdevs.sortingapp.sorting;

@FunctionalInterface
public interface ISortStrategy {
    Object[] sort(Object... collection);
}
