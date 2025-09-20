package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.comparator.ProductComparators;
import com.ftdevs.sortingapp.validation.InputValidator;

public class SortFieldSelectionState extends MenuInputState {
    @Override
    public boolean handle(ApplicationContext context) {
        Integer input = InputValidator.tryParseInteger(context.getInput());
        if (input == null) {
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        switch (input) {
            case 1 -> {
                context.setComparator(ProductComparators.BY_SKU);
                return true;
            }
            case 2 -> {
                context.setComparator(ProductComparators.BY_NAME);
                return true;
            }
            case 3 -> {
                context.setComparator(ProductComparators.BY_PRICE);
                return true;
            }
            case 4 -> {
                context.setComparator(ProductComparators.CUSTOM_PRICE);
                return true;
            }
            case 0 -> {
                context.setState(new MainMenuState());
                return true;
            }
            default -> {
                this.errorMessage = "Выбрана недопустимая опция";
                return false;
            }
        }
    }

    public SortFieldSelectionState() {
        StringBuilder sb = new StringBuilder();
        sb.append("(1) Артикул\n");
        sb.append("(2) Название\n");
        sb.append("(3) Цена\n");
        sb.append("(4) Кастомная сортировка по цене\n");
        sb.append("(0) Отмена\n");
    }
}
