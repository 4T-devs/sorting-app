package com.ftdevs.sortingapp.util;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.model.Product;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class ProductGenerator {

    private static final Random RANDOM = new Random();
    private static final String[] PRODUCT_NAMES = {
        "Смартфон", "Ноутбук", "Телевизор", "Наушники", "Планшет",
        "Мышь компьютерная", "Клавиатура", "Монитор", "Принтер", "Фотоаппарат",
        "Холодильник", "Стиральная машина", "Микроволновая печь", "Кофеварка", "Пылесос"
    };
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";

    private ProductGenerator() {}

    /**
     * Возвращает коллекцию случайных продуктов
     *
     * @param count количество продуктов для генерации
     * @return список случайных продуктов
     */
    public static CustomList<Product> generateProducts(final int count) {
        if (count <= 0) {
            throw new IllegalArgumentException(
                    "Количество продуктов должно быть положительным числом");
        }

        return IntStream.range(0, count).mapToObj(i -> createRandomProduct()).collect(collector());
    }

    /**
     * Наполняет существующую коллекцию случайными продуктами
     *
     * @param collection коллекция для наполнения
     * @param count количество продуктов для добавления
     */
    public static void fillCollection(final CustomList<Product> collection, final int count) {
        if (collection == null) {
            throw new IllegalArgumentException("Коллекция не может быть null");
        }
        if (count <= 0) {
            throw new IllegalArgumentException(
                    "Количество продуктов должно быть положительным числом");
        }

        final CustomList<Product> randomProducts = generateProducts(count);
        randomProducts.stream().forEach(collection::add);
    }

    /**
     * Генерирует случайный символ из заданной строки
     *
     * @param source строка с допустимыми символами
     * @return случайный символ в виде строки
     */
    private static String getRandomCharFromString(final String source) {
        return String.valueOf(source.charAt(RANDOM.nextInt(source.length())));
    }

    /**
     * Генерирует случайный SKU формата "XXX-000/000"
     *
     * @return случайный SKU
     */
    private static String generateRandomSku() {
        // Генерация первой части (XXX)
        final String part1 =
                IntStream.range(0, 3)
                        .mapToObj(i -> getRandomCharFromString(LETTERS))
                        .collect(Collectors.joining());

        // Генерация второй части (000)
        final String part2 =
                IntStream.range(0, 3)
                        .mapToObj(i -> getRandomCharFromString(DIGITS))
                        .collect(Collectors.joining());

        // Генерация третьей части (000)
        final String part3 =
                IntStream.range(0, 4)
                        .mapToObj(i -> getRandomCharFromString(DIGITS))
                        .collect(Collectors.joining());

        return part1 + "-" + part2 + "/" + part3;
    }

    /**
     * Генерирует случайное название продукта
     *
     * @return случайное название с уникальным номером
     */
    private static String generateRandomName() {
        final String baseName = PRODUCT_NAMES[RANDOM.nextInt(PRODUCT_NAMES.length)];
        final int uniqueNumber = RANDOM.nextInt(10_000); // от 0 до 9999
        return baseName + " #" + String.format("%04d", uniqueNumber);
    }

    /**
     * Генерирует случайную цену в диапазоне 1.0 - 1000000.0
     *
     * @return случайная цена
     */
    private static double generateRandomPrice() {
        return ThreadLocalRandom.current().nextDouble(1.0, 1_000_000.0);
    }

    /**
     * Создает один случайный продукт
     *
     * @return случайный продукт
     */
    public static Product createRandomProduct() {
        return Product.builder()
                .sku(generateRandomSku())
                .name(generateRandomName())
                .price(generateRandomPrice())
                .build();
    }

    /** Возвращает коллектор для преобразования Stream в CustomList */
    private static <T> Collector<T, ?, CustomList<T>> collector() {
        return Collector.of(
                CustomArrayList::new,
                CustomList::add,
                (left, right) -> {
                    right.forEach(left::add);
                    return left;
                },
                Collector.Characteristics.IDENTITY_FINISH);
    }
}
