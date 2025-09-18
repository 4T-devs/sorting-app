package com.ftdevs.sortingapp.util;

import static org.junit.jupiter.api.Assertions.*;

import com.ftdevs.sortingapp.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("PMD.UnitTestContainsTooManyAsserts")
class ProductValidatorTest {

    @Test
    void validateSku_ValidSku_ReturnsTrue() {
        assertTrue(ProductValidator.validateSku("ABC-123/456"));
        assertTrue(ProductValidator.validateSku("TEST-001"));
        assertTrue(ProductValidator.validateSku("PRODUCT-2024"));
        assertTrue(ProductValidator.validateSku("A")); // минимальная длина
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validateSku_NullOrEmptySku_ThrowsException(String sku) {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> ProductValidator.validateSku(sku));
        assertTrue(exception.getMessage().contains("SKU не может быть null или пустым"));
    }

    @Test
    void validateSku_TooLongSku_ThrowsException() {
        String longSku = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 26 символов

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validateSku(longSku));
        assertTrue(exception.getMessage().contains("20 символов"));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "123-ABC", // начинается с цифры
                "abc-123", // строчные буквы
                "ABC_123", // недопустимый символ _
                "ABC 123", // пробел
                "ABC@123", // недопустимый символ @
                "" // пустая строка
            })
    void validateSku_InvalidCharacters_ThrowsException(String invalidSku) {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validateSku(invalidSku));
        assertTrue(
                exception.getMessage().contains("недопустимые символы")
                        || exception.getMessage().contains("не может быть null или пустым"));
    }

    // Тесты для validateName()
    @Test
    void validateName_ValidName_ReturnsTrue() {
        assertTrue(ProductValidator.validateName("Test Product"));
        assertTrue(ProductValidator.validateName("A")); // минимальная длина
        assertTrue(ProductValidator.validateName("Product Name with Spaces and Numbers 123"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validateName_NullOrEmptyName_ThrowsException(String name) {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> ProductValidator.validateName(name));
        assertTrue(
                exception.getMessage().contains("Название продукта не может быть null или пустым"));
    }

    @Test
    void validateName_TooLongName_ThrowsException() {
        String longName = "A".repeat(101); // 101 символ

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validateName(longName));
        assertTrue(exception.getMessage().contains("100 символов"));
    }

    // Тесты для validatePrice(String)
    @Test
    void validatePriceString_ValidPrice_ReturnsTrue() {
        assertTrue(ProductValidator.validatePrice("0"));
        assertTrue(ProductValidator.validatePrice("99.99"));
        assertTrue(ProductValidator.validatePrice("1000000")); // максимальная цена
        assertTrue(ProductValidator.validatePrice("123.456"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validatePriceString_NullOrEmptyPrice_ThrowsException(String price) {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice(price));
        assertTrue(exception.getMessage().contains("Цена не может быть null или пустой"));
    }

    @Test
    void validatePriceString_InvalidNumberFormat_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice("not-a-number"));
        assertTrue(exception.getMessage().contains("Цена должна быть числом"));
    }

    @Test
    void validatePriceString_NegativePrice_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice("-10.50"));
        assertTrue(exception.getMessage().contains("Цена не может быть отрицательной"));
    }

    @Test
    void validatePriceString_TooHighPrice_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice("1000001"));
        assertTrue(exception.getMessage().contains("Цена не может превышать"));
    }

    // Тесты для validatePrice(double)
    @Test
    void validatePriceDouble_ValidPrice_ReturnsTrue() {
        assertTrue(ProductValidator.validatePrice(0.0));
        assertTrue(ProductValidator.validatePrice(99.99));
        assertTrue(ProductValidator.validatePrice(1000000.0)); // максимальная цена
    }

    @Test
    void validatePriceDouble_NegativePrice_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> ProductValidator.validatePrice(-5.0));
        assertTrue(exception.getMessage().contains("Цена не может быть отрицательной"));
    }

    @Test
    void validatePriceDouble_TooHighPrice_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice(1000000.1));
        assertTrue(exception.getMessage().contains("Цена не может превышать"));
    }

    // Тесты для createProduct()
    @Test
    void createProduct_ValidData_ReturnsProductObject() {
        Product product = ProductValidator.createProduct("TEST-123", "Test Product", "99.99");

        assertNotNull(product);
        assertEquals("TEST-123", product.getSku());
        assertEquals("Test Product", product.getName());
        assertEquals(99.99, product.getPrice(), 0.001);
    }

    @Test
    void createProduct_InvalidSku_ThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.createProduct("123-TEST", "Test Product", "99.99"));
    }

    @Test
    void createProduct_InvalidName_ThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.createProduct("TEST-123", "", "99.99"));
    }

    @Test
    void createProduct_InvalidPrice_ThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.createProduct("TEST-123", "Test Product", "-10"));
    }

    // Тесты для validateProduct()
    @Test
    void validateProduct_ValidData_ReturnsTrue() {
        assertTrue(ProductValidator.validateProduct("TEST-123", "Test Product", "99.99"));
    }

    @Test
    void validateProduct_InvalidData_ThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.validateProduct("invalid", "Test Product", "99.99"));
    }

    // Параметризованные тесты для различных сценариев
    @ParameterizedTest
    @CsvSource({
        "ABC-123/456, 'Test Product', 99.99, true",
        "123-ABC, 'Test Product', 99.99, false", // невалидный SKU
        "ABC-123, '', 99.99, false", // пустое имя
        "ABC-123, 'Test Product', -10, false", // отрицательная цена
        "ABC-123, 'Test Product', 1000001, false" // слишком высокая цена
    })
    void comprehensiveTest(String sku, String name, String price, boolean expectedValid) {
        if (expectedValid) {
            assertDoesNotThrow(() -> ProductValidator.createProduct(sku, name, price));
        } else {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> ProductValidator.createProduct(sku, name, price));
        }
    }

    // Тест на граничные значения для длины имени
    @Test
    void validateName_BoundaryValues() {
        // Максимально допустимая длина
        String maxLengthName = "A".repeat(100);
        assertTrue(ProductValidator.validateName(maxLengthName));

        // Превышение максимальной длины
        String tooLongName = "A".repeat(101);
        assertThrows(
                IllegalArgumentException.class, () -> ProductValidator.validateName(tooLongName));
    }

    // Тест на граничные значения для цены
    @Test
    void validatePrice_BoundaryValues() {
        // Граничные значения
        assertTrue(ProductValidator.validatePrice(0.0));
        assertTrue(ProductValidator.validatePrice(1000000.0));

        // За границами
        assertThrows(IllegalArgumentException.class, () -> ProductValidator.validatePrice(-0.1));
        assertThrows(
                IllegalArgumentException.class, () -> ProductValidator.validatePrice(1000000.1));
    }
}
