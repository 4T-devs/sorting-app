package com.ftdevs.sortingapp.collections;

public interface CustomList<T> {
    boolean add(T val);
    void set(int idx, T val);
    T get(int idx);
    T remove(int idx);
    int size();
}
