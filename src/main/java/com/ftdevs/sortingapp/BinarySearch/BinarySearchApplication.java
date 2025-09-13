package com.ftdevs.sortingapp.BinarySearch;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class BinarySearchApplication { //todo change generic into product, uncomment the search calls
    private BinarySearchApplication () {}
    public static <T>void binarySearch(T[] products, Scanner scanner){
        while (true) {
            System.out.println("\nВыберите поле для поиска:");
            System.out.println("1 - Артикул");
            System.out.println("2 - Название");
            System.out.println("3 - Цена");
            System.out.println("0 - Выход");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine().trim();
            if (choice.equals("0")) {
                System.out.println("Выход из программы.");
                break;
            }

            switch (choice) { //todo if it is necessary to change the declaration of the comparator,change sort method
                case "1" -> {
                    //Arrays.sort(products, Comparator.comparing(T::getArticle));
                    System.out.print("Введите артикул: ");
                    String article = scanner.nextLine().trim();
                    //T key = new T.Builder().article(article).name("X").price(0).build();
                    //int idx = BinarySearchUtil.binarySearch(products, key, Comparator.comparing(T::getArticle));
                    //printResult(products, idx);
                }
                case "2" -> {
                    //Arrays.sort(products, Comparator.comparing(T::getName));
                    System.out.print("Введите название: ");
                    String name = scanner.nextLine().trim();
                    //T key = new T.Builder().article("X").name(name).price(0).build();
                    //int idx = BinarySearchUtil.binarySearch(products, key, Comparator.comparing(T::getName));
                    //printResult(products, idx);
                }
                case "3" -> {
                    //Arrays.sort(products, Comparator.comparingDouble(T::getPrice));
                    System.out.print("Введите цену: ");
                    String priceStr = scanner.nextLine().trim();
                    try {
                        double price = Double.parseDouble(priceStr);
                        //T key = new Product.Builder().article("X").name("X").price(price).build();
                        //int idx = BinarySearchUtil.binarySearch(products, key, Comparator.comparingDouble(T::getPrice));
                        //printResult(products, idx);
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
            System.out.println("Найден объект: " + products[idx].toString());
        } else {
            System.out.println("Элемент не найден.");
        }
    }
}

