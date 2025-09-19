package com.ftdevs.sortingapp.util;

import static org.junit.jupiter.api.Assertions.*;

import com.ftdevs.sortingapp.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings({"PMD.UnitTestContainsTooManyAsserts",
        "PMD.TooManyMethods"})
final class ProductValidatorTest {
    private final String DefaultMessage = "Ошибка валидации";

    private ProductValidatorTest() {}

    @Test
    void validateSkuValidSkuReturnsTrue() {
        assertTrue(ProductValidator.validateSku("ABC-123/456"), DefaultMessage);
        assertTrue(ProductValidator.validateSku("TEST-001"), DefaultMessage);
        assertTrue(ProductValidator.validateSku("PRODUCT-2024"), DefaultMessage);
        assertTrue(ProductValidator.validateSku("A"), DefaultMessage); // минимальная длина
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validateSkuNullOrEmptySkuThrowsException(String sku) {
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
                        () -> ProductValidator.validateSku(longSku), DefaultMessage);
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
                        () -> ProductValidator.validateSku(invalidSku), DefaultMessage);
        assertTrue(
                exception.getMessage().contains("недопустимые символы")
                        || exception.getMessage().contains("не может быть null или пустым"));
    }

    @Test
    void validateName_ValidName_ReturnsTrue() {
        assertTrue(ProductValidator.validateName("Test Product"), DefaultMessage);
        assertTrue(ProductValidator.validateName("A"), DefaultMessage); // минимальная длина
        assertTrue(ProductValidator.validateName("Product Name with Spaces and Numbers 123"), DefaultMessage);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validateName_NullOrEmptyName_ThrowsException(String name) {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> ProductValidator.validateName(name), DefaultMessage);
        assertTrue(
                exception.getMessage().contains("Название продукта не может быть null или пустым"));
    }

    @Test
    void validateName_TooLongName_ThrowsException() {
        String longName = "A".repeat(101); // 101 символ

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validateName(longName), DefaultMessage);
        assertTrue(exception.getMessage().contains("100 символов"));
    }

    @Test
    void validatePriceString_ValidPrice_ReturnsTrue() {
        assertTrue(ProductValidator.validatePrice("0"), DefaultMessage);
        assertTrue(ProductValidator.validatePrice("99.99"), DefaultMessage);
        assertTrue(ProductValidator.validatePrice("1000000"), DefaultMessage); // максимальная цена
        assertTrue(ProductValidator.validatePrice("123.456"), DefaultMessage);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validatePriceString_NullOrEmptyPrice_ThrowsException(String price) {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice(price), DefaultMessage);
        assertTrue(exception.getMessage().contains("Цена не может быть null или пустой"));
    }

    @Test
    void validatePriceString_InvalidNumberFormat_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice("not-a-number"), DefaultMessage);
        assertTrue(exception.getMessage().contains("Цена должна быть числом"));
    }

    @Test
    void validatePriceString_NegativePrice_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice("-10.50"), DefaultMessage);
        assertTrue(exception.getMessage().contains("Цена не может быть отрицательной"));
    }

    @Test
    void validatePriceString_TooHighPrice_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice("1000001"), DefaultMessage);
        assertTrue(exception.getMessage().contains("Цена не может превышать"));
    }

    @Test
    void validatePriceDouble_ValidPrice_ReturnsTrue() {
        assertTrue(ProductValidator.validatePrice(0.0), DefaultMessage);
        assertTrue(ProductValidator.validatePrice(99.99), DefaultMessage);
        assertTrue(ProductValidator.validatePrice(1000000.0), DefaultMessage); // максимальная цена
    }

    @Test
    void validatePriceDouble_NegativePrice_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> ProductValidator.validatePrice(-5.0), DefaultMessage);
        assertTrue(exception.getMessage().contains("Цена не может быть отрицательной"), DefaultMessage);
    }

    @Test
    void validatePriceDouble_TooHighPrice_ThrowsException() {
        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice(1000000.1), DefaultMessage);
        assertTrue(exception.getMessage().contains("Цена не может превышать"), DefaultMessage);
    }

    @Test
    void createProduct_ValidData_ReturnsProductObject() {
        Product product = ProductValidator.createProduct("TEST-123", "Test Product", "99.99");

        assertNotNull(product);
        assertEquals("TEST-123", product.getSku(), DefaultMessage);
        assertEquals("Test Product", product.getName(), DefaultMessage);
        assertEquals(99.99, product.getPrice(), 0.001, DefaultMessage);
    }

    @Test
    void createProduct_InvalidSku_ThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.createProduct("123-TEST", "Test Product", "99.99"), DefaultMessage);
    }

    @Test
    void createProduct_InvalidName_ThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.createProduct("TEST-123", "", "99.99"), DefaultMessage);
    }

    @Test
    void createProduct_InvalidPrice_ThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.createProduct("TEST-123", "Test Product", "-10"), DefaultMessage);
    }

    @Test
    void validateProduct_ValidData_ReturnsTrue() {
        assertTrue(ProductValidator.validateProduct("TEST-123", "Test Product", "99.99"), DefaultMessage);
    }

    @Test
    void validateProduct_InvalidData_ThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.validateProduct("invalid", "Test Product", "99.99"), DefaultMessage);
    }

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
            assertDoesNotThrow(() -> ProductValidator.createProduct(sku, name, price), DefaultMessage);
        } else {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> ProductValidator.createProduct(sku, name, price), DefaultMessage);
        }
    }

    @Test
    void validateName_BoundaryValues() {
        // Максимально допустимая длина
        String maxLengthName = "A".repeat(100);
        assertTrue(ProductValidator.validateName(maxLengthName), DefaultMessage);

        // Превышение максимальной длины
        String tooLongName = "A".repeat(101);
        assertThrows(
                IllegalArgumentException.class, () -> ProductValidator.validateName(tooLongName), DefaultMessage);
    }

    @Test
    void validatePrice_BoundaryValues() {
        // Граничные значения
        assertTrue(ProductValidator.validatePrice(0.0), DefaultMessage);
        assertTrue(ProductValidator.validatePrice(1000000.0), DefaultMessage);

        // За границами
        assertThrows(IllegalArgumentException.class, () -> ProductValidator.validatePrice(-0.1), DefaultMessage);
        assertThrows(
                IllegalArgumentException.class, () -> ProductValidator.validatePrice(1000000.1), DefaultMessage);
    }
}
