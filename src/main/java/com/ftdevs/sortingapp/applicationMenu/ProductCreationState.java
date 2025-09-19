package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.entityCreators.FileEntityCreator;
import com.ftdevs.sortingapp.entityCreators.HandInput;
import com.ftdevs.sortingapp.validation.InputValidator;

public class ProductCreationState extends MenuInputState { // Меню выбора способа создания сущностей
    @Override
    public boolean handle(ApplicationContext context) {
        Integer input = InputValidator.tryParseInteger(context.getInput());
        if (input == null) {
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        switch (input) {
            case 1 -> { // Чтение объектов из файла
                context.setCreationStrategy(new FileEntityCreator());
            }
            case 2 -> { // Случайная генерация объектов
                // context.setCreationStrategy();
                System.out.println("Установить генератор");
            }
            case 3 -> { // Ручное заполнение объектов
                context.setCreationStrategy(new HandInput());
                context.setInputNeed(false);
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
        context.setState(new ProductConfigState(context.getCreationStrategy().getMessage()));
        return true;
    }

    public ProductCreationState() {
        StringBuilder sb = new StringBuilder();
        sb.append("(1) Чтение объектов из файла\n")
                .append("(2) Генерация случайных объектов\n")
                .append("(3) Ручное заполнение объектов\n")
                .append("(0) Назад\n");
        menuSelectors = sb.toString();
    }
}
