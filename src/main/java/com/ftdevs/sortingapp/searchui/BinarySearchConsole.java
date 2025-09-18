package com.ftdevs.sortingapp.searchui;

import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.model.Product;
import java.util.Optional;
import java.util.Scanner;

public final class BinarySearchConsole {

    private BinarySearchConsole() {}

    // TODO: Add a sort call before the search call
    public static void binarySearch(final CustomList<Product> products, final Scanner scanner) {
        while (true) {
            System.out.println();
            System.out.println("Выберите поле для поиска:");
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
                    System.out.print("Введите артикул: ");
                    final String sku = scanner.nextLine().trim();
                    final Optional<Integer> idxOpt = SearchService.findBySku(products, sku);
                    printResult(products, idxOpt);
                }
                case "2" -> {
                    System.out.print("Введите название: ");
                    final String name = scanner.nextLine().trim();
                    final Optional<Integer> idxOpt = SearchService.findByName(products, name);
                    printResult(products, idxOpt);
                }
                case "3" -> {
                    System.out.print("Введите цену: ");
                    final String priceStr = scanner.nextLine().trim();
                    try {
                        final double price = Double.parseDouble(priceStr);
                        final Optional<Integer> idxOpt = SearchService.findByPrice(products, price);
                        printResult(products, idxOpt);
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: введите корректное число.");
                    }
                }
                default -> System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    private static <T> void printResult(
            final CustomList<T> products, final Optional<Integer> idxOpt) {
        if (idxOpt.isPresent()) {
            final int idx = idxOpt.get();
            System.out.println("Найден объект: " + products.get(idx));
        } else {
            System.out.println("Элемент не найден.");
        }
    }
}
