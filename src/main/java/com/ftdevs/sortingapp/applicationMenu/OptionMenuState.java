package org.example.applicationMenu;

import org.example.ApplicationContext;
import org.example.validation.InputValidator;

public class OptionMenuState extends MenuInputState { //Меню опций
    @Override
    public boolean hold(ApplicationContext context) {
        Integer input = InputValidator.tryParseInteger(context.input);
        if(input == null){
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        switch (input){
            case 1 -> { //Выбор типа сущности
                context.setState(new EntitySelectionState());
                return true;
            }
            case 2 -> { //Выбор сортировки
                context.setState(new SortSelectionState());
                return true;
            }
            case 3 -> { //Назад
                context.setState(new MainMenuState());
                return true;
            }
            default -> {
                this.errorMessage = "Выбрана неверная опция";
                return false;
            }
        }
    }

    public OptionMenuState(){
        StringBuilder sb = new StringBuilder();
        sb
                .append("(1) Выбрать тип объекта\n")
                .append("(2) Выбрать алгоритм сортировки\n")
                .append("(3) Назад\n");
        menuSelectors = sb.toString();
    }

}
