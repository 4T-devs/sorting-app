package org.example.applicationMenu;

import org.example.ApplicationContext;
import org.example.validation.InputValidator;

public class EntityCreationState extends MenuInputState { //Меню выбора способа создания сущностей
    @Override
    public boolean hold(ApplicationContext context) {
        Integer input = InputValidator.tryParseInteger(context.input);
        if(input == null){
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        switch (input){
            case 1 -> { //Чтение объектов из файла
                return true;
            }
            case 2 -> { //Случайная генерация объектов
                return true;
            }
            case 3 -> { //Ручное заполнение объектов
                return true;
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
    }
    public EntityCreationState(){
        StringBuilder sb = new StringBuilder();
        sb
                .append("(1) Чтение объектов из файла\n")
                .append("(2) Генерация случайных объектов\n")
                .append("(3) Ручное заполнение объектов\n")
                .append("(4) Назад\n");
        menuSelectors = sb.toString();
    }
}
