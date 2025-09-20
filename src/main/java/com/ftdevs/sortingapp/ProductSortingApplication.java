package com.ftdevs.sortingapp;

import java.util.Scanner;

public final class ProductSortingApplication {

    private ProductSortingApplication() {}

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        final ApplicationContext context = new ApplicationContext();

        Scanner scanner = new Scanner(System.in);

        try {
            while (context.isExit()) {
                context.printHeader(); // Вывод шапки программы
                context.printMenu(); // Вывод текущего меню
                if (context.isInputNeed())
                    context.setInput(scanner.nextLine()); // Получение пользовательского ввода
                if (!context.handle())
                    context.printError(); // Вывод ошибки, если пользователь ввел недопустимые
                // параметры
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
