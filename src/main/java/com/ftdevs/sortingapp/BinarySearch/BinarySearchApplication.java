package com.ftdevs.sortingapp.BinarySearch;

import com.ftdevs.sortingapp.model.Product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class BinarySearchApplication {

    private BinarySearchApplication() {}
    //todo if it is necessary to change the declaration of the comparator,change sort method
    public static void binarySearch(Product[] products, Scanner scanner) {
        while (true) {
            System.out.println("\nВыберите поле для поиска:");
            System.out.println("1 - Артикул");
            System.out.println("2 - Название");
            System.out.println("3 - Цена");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine().trim();
            if (choice.equals("0")) {
                System.out.println("Выход из поиска.");
                break;
            }

            switch (choice) {
                case "1" -> {
                    Arrays.sort(products, Comparator.comparing(Product::getSku));
                    System.out.print("Введите артикул: ");
                    String article = scanner.nextLine().trim();
                    Product key = Product.builder()
                            .sku(article)
                            .name("dummy")
                            .price(0.0)
                            .build();
                    int idx = BinarySearchUtil.binarySearch(products, key, Comparator.comparing(Product::getSku));
                    printResult(products, idx);
                }
                case "2" -> {
                    Arrays.sort(products, Comparator.comparing(Product::getName));
                    System.out.print("Введите название: ");
                    String name = scanner.nextLine().trim();
                    Product key = Product.builder()
                            .sku("dummy")
                            .name(name)
                            .price(0.0)
                            .build();
                    int idx = BinarySearchUtil.binarySearch(products, key, Comparator.comparing(Product::getName));
                    printResult(products, idx);
                }
                case "3" -> {
                    Arrays.sort(products, Comparator.comparingDouble(Product::getPrice));
                    System.out.print("Введите цену: ");
                    String priceStr = scanner.nextLine().trim();
                    try {
                        double price = Double.parseDouble(priceStr);
                        Product key = Product.builder()
                                .sku("dummy")
                                .name("dummy")
                                .price(price)
                                .build();
                        int idx = BinarySearchUtil.binarySearch(products, key, Comparator.comparingDouble(Product::getPrice));
                        printResult(products, idx);
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: введите корректное число.");
                    }
                }
                default -> System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private static <T> void printResult(T[] products, int idx) {
        if (idx >= 0) {
            System.out.println("Найден объект: " + products[idx]);
        } else {
            System.out.println("Элемент не найден.");
        }
    }
}
