package com.ftdevs.sortingapp.collections;

import java.util.stream.Stream;

public interface CustomList<T> extends Iterable<T> {
    boolean add(T val);

    void set(int idx, T val);

    T get(int idx);

    T remove(int idx);

    int size();

    Stream<T> stream();
}
