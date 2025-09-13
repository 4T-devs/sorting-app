package com.ftdevs.sortingapp.collections;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CustomArrayListTest {

    private void fillArrays(CustomArrayList<Integer> arrayList, List<Integer> arr, int range) {
        for (int i = 0; i < range; i++) {
            arrayList.add(i);
            arr.add(i);
        }
    }

    private boolean checkArrays(CustomArrayList<Integer> arrayList, List<Integer> arr) {
        if(arrayList.size() != arr.size()) {
            System.out.println("Размеры массивов не равны");
            return false;
        }

        for(int i = 0; i < arrayList.size(); i++) {
            if(!arrayList.get(i).equals(arr.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void addAndGetTest() {

        CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        List<Integer> expectedValues = new ArrayList<>();

        fillArrays(arrayList, expectedValues, 20);
        boolean equals = checkArrays(arrayList, expectedValues);

        Assertions.assertTrue(equals);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
           arrayList.get(-1);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            arrayList.get(20);
        });
    }

    @Test
    public void setTest() {
        CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        List<Integer> expectedValues = new ArrayList<>();

        fillArrays(arrayList, expectedValues, 20);

        arrayList.set(0, 100);
        expectedValues.set(0, 100);

        arrayList.set(19, 200);
        expectedValues.set(19, 200);

        boolean equals = checkArrays(arrayList, expectedValues);

        Assertions.assertTrue(equals);
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            arrayList.set(-1, 20);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            arrayList.set(20, 200);
        });
    }

    @Test
    public void removeTest() {
        CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        List<Integer> expectedValues = new ArrayList<>();

        fillArrays(arrayList, expectedValues, 25);

        Integer removedElement = arrayList.remove(5);
        Integer expectedRemovedElement = expectedValues.remove(5);
        boolean equals = checkArrays(arrayList, expectedValues);

        Assertions.assertTrue(equals);
        Assertions.assertEquals(expectedRemovedElement, removedElement);

        int listSize = arrayList.size();
        for(int i = 0; i < listSize; i++) {
            arrayList.remove(0);
            expectedValues.remove(0);
            equals = checkArrays(arrayList, expectedValues);
            Assertions.assertTrue(equals);
        }
        Assertions.assertTrue(arrayList.size() == 0);
    }

    @Test
    public void iteratorTest() {
        CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        List<Integer> expectedValues = new ArrayList<>();

        fillArrays(arrayList, expectedValues, 25);

        Iterator<Integer> it = arrayList.iterator();
        Iterator<Integer> expectedIterator = expectedValues.iterator();

        while (it.hasNext()) {
            Assertions.assertEquals(it.next(), expectedIterator.next());
        }
    }

    @Test
    public void streamTest() {
        CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        List<Integer> expectedValues = new ArrayList<>();

        fillArrays(arrayList, expectedValues, 51);

        CustomArrayList<Integer> actualList = new CustomArrayList<>();
        arrayList.stream().filter((i) -> i % 2 == 0).forEach(actualList::add);
        List<Integer> expectedList = expectedValues.stream().filter(i -> i % 2 == 0).toList();

        Assertions.assertTrue(checkArrays(actualList, expectedList));
    }
}
