package com.ftdevs.sortingapp.util;

import com.ftdevs.sortingapp.model.Product;

public class ProductValidator {

    private ProductValidator() { }
    // Регулярное выражение для валидации SKU
    private static final String SKU_PATTERN = "^[A-Z][A-Z0-9-/]{0,19}$";
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_PRICE = 1000000; // Верхняя граница цены

    /**
     * Валидация SKU продукта
     *
     * @param sku SKU для проверки
     * @return true если валиден, иначе бросает исключение
     * @throws IllegalArgumentException с описанием ошибки
     */
    public static boolean validateSku(final String sku) {
        if (sku == null || sku.trim().isEmpty()) {
            throw new IllegalArgumentException("SKU не может быть null или пустым");
        }

        if (sku.length() > 20) {
            throw new IllegalArgumentException(
                    "SKU не может быть длиннее 20 символов. Текущая длина: " + sku.length());
        }

        if (!sku.matches(SKU_PATTERN)) {
            throw new IllegalArgumentException(
                    "SKU содержит недопустимые символы. Допустимы: A-Z, 0-9, -, /. Должен начинаться с буквы");
        }

        return true;
    }

    /**
     * Валидация имени продукта
     *
     * @param name имя для проверки
     * @return true если валиден, иначе бросает исключение
     * @throws IllegalArgumentException с описанием ошибки
     */
    public static boolean validateName(final String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть null или пустым");
        }

        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "Название продукта не может быть длиннее "
                            + MAX_NAME_LENGTH
                            + " символов. Текущая длина: "
                            + name.length());
        }

        return true;
    }

    /**
     * Валидация цены продукта (из строки)
     *
     * @param priceStr цена в виде строки
     * @return true если валиден, иначе бросает исключение
     * @throws IllegalArgumentException с описанием ошибки
     */
    public static boolean validatePrice(final String priceStr) {
        if (priceStr == null || priceStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Цена не может быть null или пустой");
        }

        try {
            double price = Double.parseDouble(priceStr);
            return validatePrice(price);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Цена должна быть числом. Получено: " + priceStr);
        }
    }

    /**
     * Валидация цены продукта (из числа)
     *
     * @param price цена в виде числа
     * @return true если валиден, иначе бросает исключение
     * @throws IllegalArgumentException с описанием ошибки
     */
    public static boolean validatePrice(final double price) {
        if (price < 0) {
            throw new IllegalArgumentException(
                    "Цена не может быть отрицательной. Получено: " + price);
        }

        if (price > MAX_PRICE) {
            throw new IllegalArgumentException(
                    "Цена не может превышать " + MAX_PRICE + ". Получено: " + price);
        }

        return true;
    }

    /**
     * Создание продукта с валидацией всех полей
     *
     * @param sku SKU продукта
     * @param name название продукта
     * @param priceStr цена продукта в виде строки
     * @return новый объект Product
     * @throws IllegalArgumentException если любое из полей невалидно
     */
    public static Product createProduct(final String sku, final String name, final String priceStr) {
        // Валидация всех полей
        validateSku(sku);
        validateName(name);
        validatePrice(priceStr);

        // Парсинг цены
        final double price = Double.parseDouble(priceStr);

        // Создание и возврат продукта
        return Product.builder().sku(sku).name(name).price(price).build();
    }

    /**
     * Комплексная проверка всех полей продукта
     *
     * @param sku SKU продукта
     * @param name название продукта
     * @param priceStr цена продукта в виде строки
     * @return true если все поля валидны, иначе бросает исключение
     * @throws IllegalArgumentException если любое из полей невалидно
     */
    public static boolean validateProduct(final String sku, final String name, final String priceStr) {
        validateSku(sku);
        validateName(name);
        validatePrice(priceStr);
        return true;
    }
}
