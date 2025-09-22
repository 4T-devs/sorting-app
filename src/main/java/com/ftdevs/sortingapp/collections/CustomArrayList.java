package com.ftdevs.sortingapp.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings({"unchecked", "PMD.TooManyMethods"})
public class CustomArrayList<T> implements CustomList<T> {

    private static final int INITIAL_CAPACITY = 10;
    private Object[] array;
    private int size;

    public CustomArrayList(final int initialCapacity) {
        array = new Object[initialCapacity];
        size = 0;
    }

    public CustomArrayList() {
        this(INITIAL_CAPACITY);
    }

    @Override
    public boolean add(final T value) {
        if (array.length == size) {
            resizeArray();
        }

        array[size++] = value;
        return true;
    }

    private void resizeArray() {
        final Object[] newArray = new Object[array.length * 2];
        System.arraycopy(array, 0, newArray, 0, array.length);

        this.array = newArray;
    }

    @Override
    public void set(final int idx, final T value) {
        checkIndex(idx);
        array[idx] = value;
    }

    @Override
    public T get(final int idx) {
        checkIndex(idx);
        return (T) array[idx];
    }

    @Override
    public T remove(final int idx) {
        final T element = get(idx);
        shiftArray(idx);
        return element;
    }

    private void shiftArray(final int idx) {
        for (int i = idx + 1; i < size; i++) {
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
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < size; i++) {
            stringBuilder.append(array[i]);
            if (i != (size - 1)) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private void checkIndex(final int idx) {
        if (idx >= size || idx < 0) {
            throw new IndexOutOfBoundsException(
                    "Index " + idx + " out of bounds for length " + size);
        }
    }

    @Override
    @SuppressWarnings("PMD.MethodArgumentCouldBeFinal")
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
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                removeElement = pos;
                return (T) array[pos++];
            }

            @Override
            public void remove() {
                if (removeElement < 0) {
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

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    @SuppressWarnings("PMD.MethodArgumentCouldBeFinal")
    public void forEach(Consumer<? super T> action) {
        for (int i = 0; i < size; i++) {
            action.accept(get(i));
        }
    }
}
