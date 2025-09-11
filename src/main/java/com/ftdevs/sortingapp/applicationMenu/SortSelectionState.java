package org.example.applicationMenu;

import org.example.ApplicationContext;
import org.example.validation.InputValidator;

public class SortSelectionState extends MenuInputState { //Меню выбора алгоритма сортировки


    @Override
    public boolean hold(ApplicationContext context) {
        Integer input = InputValidator.tryParseInteger(context.input);
        if(input == null){
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        switch (input){
            case 1 -> { //Алгоритм 1
                return true;
            }
            case 2 -> { //Алгоритм 2
                return true;
            }
            case 3 -> { //Алгоритм 3
                return true;
            }
            case 4 -> {
                context.setState(new OptionMenuState());
                return true;
            }
            default -> {
                this.errorMessage = "Выбрана неверная опция";
                return false;
            }
        }
    }

    public SortSelectionState(){
        StringBuilder sb = new StringBuilder();
        sb
                .append("(1) Алгоритм 1\n")
                .append("(2) Алгоритм 2\n")
                .append("(3) Алгоритм 3\n")
                .append("(4) Назад\n");
        menuSelectors = sb.toString();
    }
}
