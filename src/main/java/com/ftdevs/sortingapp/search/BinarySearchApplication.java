package com.ftdevs.sortingapp.search;

import com.ftdevs.sortingapp.model.Product;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public final class BinarySearchApplication {

    private BinarySearchApplication() {}

    // TODO: if it is necessary to change the declaration of the comparator, change sort method
    public static void binarySearch(final Product[] products, final Scanner scanner) {
        while (true) {
            System.out.println("\nВыберите поле для поиска:");
            System.out.println("1 - Артикул");
            System.out.println("2 - Название");
            System.out.println("3 - Цена");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            final String choice = scanner.nextLine().trim();
            if ("0".equals(choice)) {
                System.out.println("Выход из поиска.");
                break;
            }

            switch (choice) {
                case "1" -> {
                    Arrays.sort(products, Comparator.comparing(Product::getSku));
                    System.out.print("Введите артикул: ");
                    final String article = scanner.nextLine().trim();
                    final Product key =
                            Product.builder().sku(article).name("dummy").price(0.0).build();
                    final int idx =
                            BinarySearchUtil.binarySearch(
                                    products, key, Comparator.comparing(Product::getSku));
                    printResult(products, idx);
                }
                case "2" -> {
                    Arrays.sort(products, Comparator.comparing(Product::getName));
                    System.out.print("Введите название: ");
                    final String name = scanner.nextLine().trim();
                    final Product key =
                            Product.builder().sku("dummy").name(name).price(0.0).build();
                    final int idx =
                            BinarySearchUtil.binarySearch(
                                    products, key, Comparator.comparing(Product::getName));
                    printResult(products, idx);
                }
                case "3" -> {
                    Arrays.sort(products, Comparator.comparingDouble(Product::getPrice));
                    System.out.print("Введите цену: ");
                    final String priceStr = scanner.nextLine().trim();
                    try {
                        final double price = Double.parseDouble(priceStr);
                        final Product key =
                                Product.builder().sku("dummy").name("dummy").price(price).build();
                        final int idx =
                                BinarySearchUtil.binarySearch(
                                        products,
                                        key,
                                        Comparator.comparingDouble(Product::getPrice));
                        printResult(products, idx);
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: введите корректное число.");
                    }
                }
                default -> System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private static <T> void printResult(final T[] products, final int idx) {
        if (idx >= 0) {
            System.out.println("Найден объект: " + products[idx]);
        } else {
            System.out.println("Элемент не найден.");
        }
    }
}
