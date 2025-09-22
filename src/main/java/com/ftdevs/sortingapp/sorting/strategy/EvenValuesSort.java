package com.ftdevs.sortingapp.sorting.strategy;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import java.util.Comparator;
import java.util.function.ToIntFunction;

public class EvenValuesSort<T> implements ISortStrategy<T> {

    private final ISortStrategy<T> sortStrategy;
    private final ToIntFunction<T> valuesExtractor;

    /**
     * @param sortStrategy используемая сортировка (можно и однопоточную)
     * @param valuesExtractor сюда нужно передать функцию, преобразующую параметр T в Integer
     */
    public EvenValuesSort(ISortStrategy<T> sortStrategy, ToIntFunction<T> valuesExtractor) {
        this.sortStrategy = sortStrategy;
        this.valuesExtractor = valuesExtractor;
    }

    @Override
    public void sort(CustomList<T> collection, Comparator<T> comparator) {
        CustomList<T> evenValues = getEvenValues(collection);

        // Сортировка четных значений
        sortStrategy.sort(evenValues, comparator);

        // Вставка четных чисел обратно в массив в отсортированном порядке
        writeBackEvenValues(evenValues, collection);
    }

    private CustomList<T> getEvenValues(CustomList<T> collection) {
        CustomList<T> evenValues = new CustomArrayList<>();

        for (int i = 0; i < collection.size(); i++) {
            T val = collection.get(i);
            if (isEven(val)) {
                evenValues.add(val);
            }
        }

        return evenValues;
    }

    private boolean isEven(T value) {
        return valuesExtractor.applyAsInt(value) % 2 == 0;
    }

    private void writeBackEvenValues(CustomList<T> evenValues, CustomList<T> collection) {

        int posInEvenValues = 0;
        for (int i = 0; i < collection.size(); i++) {

            T val = collection.get(i);

            if (isEven(val)) {
                T evenVal = evenValues.get(posInEvenValues++);
                collection.set(i, evenVal);
            }
        }
    }
}
