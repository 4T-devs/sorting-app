package com.ftdevs.sortingapp.sorting.strategy;

import com.ftdevs.sortingapp.collections.CustomList;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;

@SuppressWarnings("PMD.AtLeastOneConstructor")
public class QuickSort<T> implements ISortStrategy<T> {

    @Override
    public void sort(final CustomList<T> list, final Comparator<T> comparator) {
        if (list == null || list.size() <= 1 || comparator == null) {
            return;
        }

        iterativeQuickSort(list, list.size() - 1, comparator);
    }

    private void iterativeQuickSort(
            final CustomList<T> list, final int high, final Comparator<T> comparator) {
        final Deque<Integer> stack = new ArrayDeque<>();
        stack.push(0);
        stack.push(high);

        while (!stack.isEmpty()) {
            final int currentHigh = stack.pop();
            final int currentLow = stack.pop();

            if (currentLow >= currentHigh) {
                continue;
            }

            final int pivotIndex = partition(list, currentLow, currentHigh, comparator);

            if (pivotIndex - 1 > currentLow) {
                stack.push(currentLow);
                stack.push(pivotIndex - 1);
            }

            if (pivotIndex + 1 < currentHigh) {
                stack.push(pivotIndex + 1);
                stack.push(currentHigh);
            }
        }
    }

    private int partition(
            final CustomList<T> list,
            final int low,
            final int high,
            final Comparator<T> comparator) {
        final int middle = low + (high - low) / 2;
        final int pivotIndex = medianOfThree(list, low, middle, high, comparator);
        swap(list, pivotIndex, high);
        final T pivot = list.get(high);
        int index = low;
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                swap(list, index, j);
                index++;
            }
        }

        swap(list, index, high);
        return index;
    }

    private int medianOfThree(
            final CustomList<T> list,
            final int low,
            final int middle,
            final int high,
            final Comparator<T> comparator) {
        final T leftValue = list.get(low);
        final T middleValue = list.get(middle);
        final T rightValue = list.get(high);
        final int result;
        if (comparator.compare(leftValue, middleValue) < 0) {
            if (comparator.compare(middleValue, rightValue) < 0) {
                result = middle; // leftValue < middleValue < rightValue
            } else if (comparator.compare(leftValue, rightValue) < 0) {
                result = high; // leftValue < rightValue <= middleValue
            } else {
                result = low; // rightValue <= leftValue < middleValue
            }
        } else {
            if (comparator.compare(leftValue, rightValue) < 0) {
                result = low; // middleValue <= leftValue < rightValue
            } else if (comparator.compare(middleValue, rightValue) < 0) {
                result = high; // middleValue < rightValue <= leftValue
            } else {
                result = middle; // rightValue <= middleValue <= leftValue
            }
        }

        return result;
    }

    private void swap(final CustomList<T> list, final int firstIndex, final int secondIndex) {
        final T temp = list.get(firstIndex);
        list.set(firstIndex, list.get(secondIndex));
        list.set(secondIndex, temp);
    }
}
