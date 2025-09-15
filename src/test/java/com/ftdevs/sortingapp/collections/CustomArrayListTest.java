package com.ftdevs.sortingapp.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.UnitTestContainsTooManyAsserts")
final class CustomArrayListTest {

    private static final String GET_MESSAGE = "Method get() is incorrect";
    private static final String ARRAYS_NOT_EQUAL = "Arrays are not equal";
    private static final String ITERATORS = "Actual iterator is not equal to expected iterator";
    private static final String REMOVE_MESSAGE = "Method Remove is incorrect";

    private CustomArrayListTest() {}

    private void fillArrays(
            final CustomArrayList<Integer> arrayList, final List<Integer> arr, final int range) {
        for (int i = 0; i < range; i++) {
            arrayList.add(i);
            arr.add(i);
        }
    }

    private boolean checkArrays(final CustomArrayList<Integer> arrayList, final List<Integer> arr) {

        boolean equals = true;

        if (arrayList.size() != arr.size()) {
            System.out.println("Размеры массивов не равны");
            equals = false;
        }

        for (int i = 0; i < arrayList.size(); i++) {
            if (!arrayList.get(i).equals(arr.get(i))) {
                equals = false;
            }
        }

        return equals;
    }

    @Test
    void addTest() {

        final CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        final List<Integer> expectedValues = new ArrayList<>();

        fillArrays(arrayList, expectedValues, 20);

        assertTrue(checkArrays(arrayList, expectedValues), ARRAYS_NOT_EQUAL);
    }

    @Test
    @SuppressWarnings("PMD.LinguisticNaming")
    void getTest() {

        final CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        final List<Integer> expectedValues = new ArrayList<>();

        fillArrays(arrayList, expectedValues, 20);
        final boolean arraysEquals = checkArrays(arrayList, expectedValues);
        boolean indexException1 = false;

        try {
            arrayList.get(-1);
        } catch (IndexOutOfBoundsException e) {
            indexException1 = true;
        }

        boolean indexException2 = false;
        try {
            arrayList.get(20);
        } catch (IndexOutOfBoundsException e) {
            indexException2 = true;
        }

        assertTrue(arraysEquals && indexException1 && indexException2, GET_MESSAGE);
    }

    @Test
    void setTest() {
        final CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        final List<Integer> expectedValues = new ArrayList<>();

        fillArrays(arrayList, expectedValues, 20);

        arrayList.set(0, 100);
        expectedValues.set(0, 100);

        arrayList.set(19, 200);
        expectedValues.set(19, 200);

        final boolean equals = checkArrays(arrayList, expectedValues);

        assertTrue(equals, ARRAYS_NOT_EQUAL);
    }

    @Test
    void removeTest() {
        final CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        final List<Integer> expectedValues = new ArrayList<>();

        fillArrays(arrayList, expectedValues, 25);

        final Integer removedElement = arrayList.remove(5);
        final Integer expectedElement = expectedValues.remove(5);
        boolean equals = checkArrays(arrayList, expectedValues);

        final int listSize = arrayList.size();
        for (int i = 0; i < listSize && equals; i++) {
            arrayList.remove(0);
            expectedValues.remove(0);
            equals = checkArrays(arrayList, expectedValues);
        }

        equals &= expectedElement.equals(removedElement) && arrayList.size() == 0;

        assertTrue(equals, REMOVE_MESSAGE);
    }

    @Test
    void iteratorTest() {
        final CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        final List<Integer> expectedValues = new ArrayList<>();

        fillArrays(arrayList, expectedValues, 25);

        final Iterator<Integer> iterator = arrayList.iterator();
        final Iterator<Integer> expectedIterator = expectedValues.iterator();

        boolean equals = true;
        while (iterator.hasNext() && equals) {
            equals = iterator.next().equals(expectedIterator.next());
        }

        assertTrue(equals, ITERATORS);
    }

    @Test
    void streamTest() {
        final CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        final List<Integer> expectedValues = new ArrayList<>();

        fillArrays(arrayList, expectedValues, 51);

        final CustomArrayList<Integer> actualList = new CustomArrayList<>();
        arrayList.stream().filter((i) -> i % 2 == 0).forEach(actualList::add);
        final List<Integer> expectedList = expectedValues.stream().filter(i -> i % 2 == 0).toList();

        assertTrue(checkArrays(actualList, expectedList), ARRAYS_NOT_EQUAL);
    }

    @Test
    void forEachTest() {
        final CustomArrayList<Integer> customArrayList = new CustomArrayList<>();
        final List<Integer> arrayList = new ArrayList<>();
        fillArrays(customArrayList, arrayList, 10);

        final List<Integer> actualList = new ArrayList<>();
        final List<Integer> expectedList = new ArrayList<>();

        customArrayList.forEach((v) -> actualList.add(v * v));
        arrayList.forEach((v) -> expectedList.add(v * v));

        assertEquals(actualList, expectedList, ARRAYS_NOT_EQUAL);
    }
}
