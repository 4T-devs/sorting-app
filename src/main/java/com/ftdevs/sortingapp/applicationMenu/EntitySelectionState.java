package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.entities.Entity;
import com.ftdevs.sortingapp.validation.InputValidator;

public class EntitySelectionState extends MenuInputState {

    @Override
    public boolean handle(ApplicationContext context) { //Меню выбора сущностей, с которыми будем работать
        Integer input = InputValidator.tryParseInteger(context.input);
        if(input == null){
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        switch (input){
            case 1 -> { //Тип 1
                context.entityType = Entity.EntityBuilder.class; //TODO Подставить билдер нужной сущности
                return true;
            }
            case 2 -> { //Тип 2
                context.entityType = Entity.EntityBuilder.class; //TODO Подставить билдер нужной сущности
                return true;
            }
            case 3 -> { //Тип 3
                context.entityType = Entity.EntityBuilder.class; //TODO Подставить билдер нужной сущности
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

    public EntitySelectionState(){
        StringBuilder sb = new StringBuilder();
        sb
                .append("(1) Тип 1\n")
                .append("(2) Тип 2\n")
                .append("(3) Тип 3\n")
                .append("(4) Назад\n");
        //TODO Подставить конкретные типы сущностей
        menuSelectors = sb.toString();
    }
}
