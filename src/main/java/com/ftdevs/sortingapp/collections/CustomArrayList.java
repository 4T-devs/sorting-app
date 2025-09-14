package com.ftdevs.sortingapp.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("all")
public class CustomArrayList<T> implements CustomList<T>, Iterable<T> {

    private final static int DEFAULT_INITIAL_CAPACITY = 10;
    private Object[] array;
    private int size;

    public CustomArrayList(int initialCapacity) {
        array = new Object[initialCapacity];
        size = 0;
    }

    public CustomArrayList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    @Override
    public boolean add(T value) {
        if(array.length == size) {
            resizeArray();
        }
        array[size++] = value;
        return true;
    }

    private void resizeArray() {
        Object[] newArray = new Object[array.length * 2];
        for(int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        this.array = newArray;
    }

    @Override
    public void set(int idx, T value) {
        checkIndex(idx);
        array[idx] = value;
    }

    @Override
    public T get(int idx) {
        checkIndex(idx);
        return (T) array[idx];
    }

    @Override
    public T remove(int idx) {
        T el = get(idx);
        shiftArray(idx);
        return el;
    }

    private void shiftArray(int idx) {
        for(int i = idx + 1; i < size; i++) {
            array[i - 1] = array[i];
        }
        array[--size] = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for(int i = 0; i < size; i++) {
            stringBuilder.append(array[i]);
            if(i != (size - 1)) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private void checkIndex(int idx) {
        if(idx >= size || idx < 0) {
            throw new IndexOutOfBoundsException("Index " + idx + " out of bounds for length " + size);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int pos = 0;
            private int removeElement = -1;

            @Override
            public boolean hasNext() {
                return pos < size;
            }

            @Override
            public T next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                }
                removeElement = pos;
                return (T) array[pos++];
            }

            @Override
            public void remove() {
                if(removeElement < 0){
                    throw new IllegalStateException("next() should be called before remove()");
                }
                CustomArrayList.this.remove(removeElement);
                pos = removeElement;
                removeElement = -1;
            }

            @Override
            public void forEachRemaining(Consumer<? super T> action) {
                Iterator.super.forEachRemaining(action);
            }
        };
    }
    @Override
    public Stream<T> stream() {
        return IntStream.range(0, size).mapToObj(this::get);
    }
}
