package com.ftdevs.sortingapp.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import org.junit.jupiter.api.Test;

final class ConcurrentCounterTest {

    private ConcurrentCounterTest() {}

    @Test
    void shouldCountOccurrencesSingleThread() {
        final CustomList<Integer> list = new CustomArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i % 10);
        }
        final int count = ConcurrentCounter.countOccurrences(list, 5, 1);
        assertEquals(10, count, "Ожидалось 10");
    }

    @Test
    void shouldCountOccurrencesMultiThread() {
        final CustomList<String> list = new CustomArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("hello");
        }
        for (int i = 0; i < 30; i++) {
            list.add("world");
        }
        final int count = ConcurrentCounter.countOccurrences(list, "hello", 4);
        assertEquals(50, count, "Ожидался 0");
    }

    @Test
    void shouldReturnZeroWhenNotFound() {
        final CustomList<Integer> list = new CustomArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        final int count = ConcurrentCounter.countOccurrences(list, 999, 3);
        assertEquals(0, count, "Ожидался 0");
    }

    @Test
    void shouldHandleEmptyList() {
        final CustomList<String> list = new CustomArrayList<>();
        final int count = ConcurrentCounter.countOccurrences(list, "x", 2);
        assertEquals(0, count, "Ожидался 0");
    }
}
