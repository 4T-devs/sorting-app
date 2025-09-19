package com.ftdevs.sortingapp.util;

import static org.junit.jupiter.api.Assertions.*;

import com.ftdevs.sortingapp.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings({"PMD.UnitTestContainsTooManyAsserts", "PMD.TooManyMethods"})
final class ProductValidatorTest {
    private static final String INCORRECT_MSG = "Ошибка валидации";

    private ProductValidatorTest() {}

    @Test
    void validateSkuValidSkuReturnsTrue() {
        assertTrue(ProductValidator.validateSku("ABC-123/456"), INCORRECT_MSG);
        assertTrue(ProductValidator.validateSku("TEST-001"), INCORRECT_MSG);
        assertTrue(ProductValidator.validateSku("PRODUCT-2024"), INCORRECT_MSG);
        assertTrue(ProductValidator.validateSku("A"), INCORRECT_MSG); // минимальная длина
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validateSkuNullOrEmptySkuThrowsException(final String sku) {
        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class, () -> ProductValidator.validateSku(sku));
        assertTrue(
                exception.getMessage().contains("SKU не может быть null или пустым"),
                INCORRECT_MSG);
    }

    @Test
    void validateSkuTooLongSkuThrowsException() {
        final String longSku = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 26 символов

        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validateSku(longSku),
                        INCORRECT_MSG);
        assertTrue(exception.getMessage().contains("20 символов"), INCORRECT_MSG);
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
    void validateSkuInvalidCharactersThrowsException(final String invalidSku) {
        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validateSku(invalidSku),
                        INCORRECT_MSG);
        assertTrue(
                exception.getMessage().contains("недопустимые символы")
                        || exception.getMessage().contains("не может быть null или пустым"),
                INCORRECT_MSG);
    }

    @Test
    void validateNameValidNameReturnsTrue() {
        assertTrue(ProductValidator.validateName("Test Product"), INCORRECT_MSG);
        assertTrue(ProductValidator.validateName("A"), INCORRECT_MSG); // минимальная длина
        assertTrue(
                ProductValidator.validateName("Product Name with Spaces and Numbers 123"),
                INCORRECT_MSG);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validateNameNullOrEmptyNameThrowsException(final String name) {
        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validateName(name),
                        INCORRECT_MSG);
        assertTrue(
                exception.getMessage().contains("Название продукта не может быть null или пустым"),
                INCORRECT_MSG);
    }

    @Test
    void validateNameTooLongNameThrowsException() {
        final String longName = "A".repeat(101); // 101 символ

        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validateName(longName),
                        INCORRECT_MSG);
        assertTrue(exception.getMessage().contains("100 символов"), INCORRECT_MSG);
    }

    @Test
    void validatePriceStringValidPrice() {
        assertTrue(ProductValidator.validatePrice("0"), INCORRECT_MSG);
        assertTrue(ProductValidator.validatePrice("99.99"), INCORRECT_MSG);
        assertTrue(ProductValidator.validatePrice("1000000"), INCORRECT_MSG); // максимальная цена
        assertTrue(ProductValidator.validatePrice("123.456"), INCORRECT_MSG);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void validatePriceStringNullOrEmptyPriceThrowsException(final String price) {
        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice(price),
                        INCORRECT_MSG);
        assertTrue(
                exception.getMessage().contains("Цена не может быть null или пустой"),
                INCORRECT_MSG);
    }

    @Test
    void validatePriceStringInvalidNumberFormatThrowsException() {
        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice("not-a-number"),
                        INCORRECT_MSG);
        assertTrue(exception.getMessage().contains("Цена должна быть числом"), INCORRECT_MSG);
    }

    @Test
    void validatePriceStringNegativePriceThrowsException() {
        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice("-10.50"),
                        INCORRECT_MSG);
        assertTrue(
                exception.getMessage().contains("Цена не может быть отрицательной"), INCORRECT_MSG);
    }

    @Test
    void validatePriceStringTooHighPriceThrowsException() {
        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice("1000001"),
                        INCORRECT_MSG);
        assertTrue(exception.getMessage().contains("Цена не может превышать"), INCORRECT_MSG);
    }

    @Test
    void validatePriceDoubleValidPriceReturnsTrue() {
        assertTrue(ProductValidator.validatePrice(0.0), INCORRECT_MSG);
        assertTrue(ProductValidator.validatePrice(99.99), INCORRECT_MSG);
        assertTrue(ProductValidator.validatePrice(1_000_000.0), INCORRECT_MSG); // максимальная цена
    }

    @Test
    void validatePriceDoubleNegativePriceThrowsException() {
        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> ProductValidator.validatePrice(-5.0),
                        INCORRECT_MSG);
        assertTrue(
                exception.getMessage().contains("Цена не может быть отрицательной"), INCORRECT_MSG);
    }

    @Test
    void createProductValidDataReturnsProductObject() {
        final Product product = ProductValidator.createProduct("TEST-123", "Test Product", "99.99");

        assertNotNull(product, INCORRECT_MSG);
        assertEquals("TEST-123", product.getSku(), INCORRECT_MSG);
        assertEquals("Test Product", product.getName(), INCORRECT_MSG);
        assertEquals(99.99, product.getPrice(), 0.001, INCORRECT_MSG);
    }

    @Test
    void createProductInvalidSkuThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.createProduct("123-TEST", "Test Product", "99.99"),
                INCORRECT_MSG);
    }

    @Test
    void createProductInvalidNameThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.createProduct("TEST-123", "", "99.99"),
                INCORRECT_MSG);
    }

    @Test
    void createProductInvalidPriceThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.createProduct("TEST-123", "Test Product", "-10"),
                INCORRECT_MSG);
    }

    @Test
    void validateProductValidDataReturnsTrue() {
        assertDoesNotThrow(
                () -> ProductValidator.validateProduct("TEST-123", "Test Product", "99.99"),
                INCORRECT_MSG);
    }

    @Test
    void validateProductInvalidDataThrowsException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.validateProduct("invalid", "Test Product", "99.99"),
                INCORRECT_MSG);
    }

    @ParameterizedTest
    @CsvSource({
        "ABC-123/456, 'Test Product', 99.99, true",
        "123-ABC, 'Test Product', 99.99, false", // невалидный SKU
        "ABC-123, '', 99.99, false", // пустое имя
        "ABC-123, 'Test Product', -10, false", // отрицательная цена
        "ABC-123, 'Test Product', 1_000_001, false" // слишком высокая цена
    })
    void comprehensiveTest(
            final String sku, final String name, final String price, final boolean expectedValid) {
        if (expectedValid) {
            assertDoesNotThrow(
                    () -> ProductValidator.createProduct(sku, name, price), INCORRECT_MSG);
        } else {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> ProductValidator.createProduct(sku, name, price),
                    INCORRECT_MSG);
        }
    }

    @Test
    void validateNameBoundaryValues() {
        // Максимально допустимая длина
        final String maxLengthName = "A".repeat(100);
        assertTrue(ProductValidator.validateName(maxLengthName), INCORRECT_MSG);

        // Превышение максимальной длины
        final String tooLongName = "A".repeat(101);
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.validateName(tooLongName),
                INCORRECT_MSG);
    }

    @Test
    void validatePriceBoundaryValues() {
        // Граничные значения
        assertTrue(ProductValidator.validatePrice(0.0), INCORRECT_MSG);
        assertTrue(ProductValidator.validatePrice(1_000_000.0), INCORRECT_MSG);

        // За границами
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.validatePrice(-0.1),
                INCORRECT_MSG);
        assertThrows(
                IllegalArgumentException.class,
                () -> ProductValidator.validatePrice(1_000_000.1),
                INCORRECT_MSG);
    }
}
