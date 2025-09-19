package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.validation.InputValidator;

public class ProductSearchState extends MenuInputState {
    @Override
    public boolean handle(ApplicationContext context) {
        Integer input = InputValidator.tryParseInteger(context.getInput());
        if (input == null) {
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        switch (input) {
            case 1 -> {
                context.setState(new BinarySearchState(1));
                return true;
            }
            case 2 -> {
                context.setState(new BinarySearchState(2));
                return true;
            }
            case 3 -> {
                context.setState(new BinarySearchState(3));
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

    public ProductSearchState() {
        StringBuilder sb = new StringBuilder();
        sb.append("(1) Артикул\n");
        sb.append("(2) Название\n");
        sb.append("(3) Цена\n");
        sb.append("(0) Назад\n");
        menuSelectors = sb.toString();
    }
}
