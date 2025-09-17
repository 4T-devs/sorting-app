package com.ftdevs.sortingapp;

public final class ProductSortingApplication {

    private ProductSortingApplication() {}

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        final ApplicationContext context = new ApplicationContext();

        IOSingleton.getInstance().setOutput(System.out);
        IOSingleton.getInstance().setInput(System.in);

        try {
            while (context.isExit()) {
                context.printHeader(); // Вывод шапки программы
                context.printMenu(); // Вывод текущего меню
                context.setInput(
                        IOSingleton.getInstance().readLine()); // Получение пользовательского ввода
                if (!context.handle())
                    context.printError(); // Вывод ошибки, если пользователь ввел недопустимые
                // параметры
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
