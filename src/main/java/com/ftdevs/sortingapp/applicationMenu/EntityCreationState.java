package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.entityCreators.FileReader;
import com.ftdevs.sortingapp.entityCreators.HandInput;
import com.ftdevs.sortingapp.entityCreators.RandomCreator;
import com.ftdevs.sortingapp.validation.InputValidator;

public class EntityCreationState extends MenuInputState { // Меню выбора способа создания сущностей
    @Override
    public boolean handle(ApplicationContext context) {
        Integer input = InputValidator.tryParseInteger(context.getInput());
        if (input == null) {
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        switch (input) {
            case 1 -> { // Чтение объектов из файла
                context.setCreationStrategy(new FileReader());
            }
            case 2 -> { // Случайная генерация объектов
                context.setCreationStrategy(new RandomCreator());
            }
            case 3 -> { // Ручное заполнение объектов
                context.setCreationStrategy(new HandInput());
            }
            case 4 -> {
                context.setState(new MainMenuState());
                return true;
            }
            default -> {
                this.errorMessage = "Выбрана неверная опция";
                return false;
            }
        }
        context.setState(new EntityCreationConfigState(context.getCreationStrategy().getMessage()));
        return true;
    }

    public EntityCreationState() {
        StringBuilder sb = new StringBuilder();
        sb.append("(1) Чтение объектов из файла\n")
                .append("(2) Генерация случайных объектов\n")
                .append("(3) Ручное заполнение объектов\n")
                .append("(4) Назад\n");
        menuSelectors = sb.toString();
    }
}
