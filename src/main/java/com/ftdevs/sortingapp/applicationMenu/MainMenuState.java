package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.validation.InputValidator;

public class MainMenuState extends MenuInputState {

    @Override
    public boolean handle(ApplicationContext context) { // Главное меню
        Integer input = InputValidator.tryParseInteger(context.getInput());
        if (input == null) {
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        switch (input) {
            case 1 -> { // Заполнение данными
                context.setState(new ProductCreationState());
                return true;
            }
            case 2 -> { // Выбрать алгоритм сортировки
                context.setState(new SortSelectionState());
                return true;
            }
            case 3 -> { // Отсортировать по полю
                context.sort();
                return true;
            }
            case 4 -> { // Поиск
                context.setState(new ProductSearchState());
                return true;
            }
            case 5 -> { // Показать продукты
                context.printObjects();
                return true;
            }
            case 6 -> { // Сохранить в файл
                return true;
            }
            case 7 -> { // Подсчитать вхождения
                context.setState(new ProductCountingState());
                return true;
            }
            case 0 -> { // Выход
                context.exit();
                System.out.println("Завершение работы..");
                System.out.println("Exit code 0");
                return true;
            }
            default -> {
                this.errorMessage = "Выбрана неверная опция";
                return false;
            }
        }
    }

    public MainMenuState() {
        StringBuilder sb = new StringBuilder();
        sb.append("(1) Заполнить данные\n")
                .append("(2) Выбор алгоритма сортировки\n")
                .append("(3) Отсортировать по полю\n")
                .append("(4) Поиск\n")
                .append("(5) Показать продукты\n")
                .append("(6) Сохранить в файл\n")
                .append("(7) Подсчитать вхождения\n")
                .append("(0) Выход\n");
        menuSelectors = sb.toString();
    }
}
