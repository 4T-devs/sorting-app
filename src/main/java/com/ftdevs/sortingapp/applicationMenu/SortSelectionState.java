package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.sorting.InsertionSorting;
import com.ftdevs.sortingapp.sorting.QuickSort;
import com.ftdevs.sortingapp.validation.InputValidator;

public class SortSelectionState extends MenuInputState { // Меню выбора алгоритма сортировки

    @Override
    public boolean handle(ApplicationContext context) {
        Integer input = InputValidator.tryParseInteger(context.getInput());
        if (input == null) {
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        switch (input) {
            case 1 -> {
                context.setSortStrategy(new QuickSort<>());
                context.setState(new MainMenuState());
                return true;
            }
            case 2 -> {
                context.setSortStrategy(new InsertionSorting<>());
                context.setState(new MainMenuState());
                return true;
            }
            case 0 -> {
                context.setState(new MainMenuState());
                return true;
            }
            default -> {
                this.errorMessage = "Выбрана неверная опция";
                return false;
            }
        }
    }

    public SortSelectionState() {
        StringBuilder sb = new StringBuilder();
        sb.append("(1) Быстрая сортировка\n")
                .append("(2) Сортировка вставками\n")
                .append("(0) Назад\n");
        menuSelectors = sb.toString();
    }
}
