package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.validation.InputValidator;

public class MainMenuState extends MenuInputState {

    @Override
    public boolean handle(ApplicationContext context) { //Главное меню
        Integer input = InputValidator.tryParseInteger(context.input);
        if(input == null){
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        switch (input){
            case 1 -> { //Просмотреть текущие объекты
                context.printObjects();
                return true;
            }
            case 2 -> { //Добавить объекты
                context.setState(new EntityCreationState());
                return true;
            }
            case 3 -> { //Сортировка объектов
                context.sort();
                return true;
            }
            case 4 -> { //Опции
                context.setState(new OptionMenuState());
                return true;
            }
            case 5 -> { //Выход
                context.exitFlag = false;
                return true;
            }
            default -> {
                this.errorMessage = "Выбрана неверная опция";
                return false;
            }
        }
    }


    public MainMenuState(){
        StringBuilder sb = new StringBuilder();
        sb
                .append("(1) Посмотреть объекты\n")
                .append("(2) Добавить объекты\n")
                .append("(3) Сортировка объектов\n")
                .append("(4) Опции\n")
                .append("(5) Выход\n");
        menuSelectors = sb.toString();
    }
}