package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.entities.FieldOrder;
import com.ftdevs.sortingapp.validation.InputValidator;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class EntitySearchState extends MenuInputState {
    @Override
    public boolean handle(ApplicationContext context) {
        Integer input = InputValidator.tryParseInteger(context.getInput());
        if (input == null) {
            this.errorMessage = "Неверный формат вводимых данных";
            return false;
        }

        var fields =
                Arrays.stream(context.getEntityType().getDeclaredFields())
                        .sorted(
                                Comparator.comparing(
                                        field -> field.getAnnotation(FieldOrder.class).value()))
                        .toList();

        if (input >= 0 && input <= fields.size()) {
            if (input == 0) {
                context.setState(new MainMenuState());
            } else {
                context.setSortField(fields.get(input - 1));
            }
            return true;
        } else {
            this.errorMessage = "Выбрана недопустимая опция";
            return false;
        }
    }

    public EntitySearchState(ApplicationContext context) {
        var fields =
                Arrays.stream(context.getEntityType().getDeclaredFields())
                        .sorted(
                                Comparator.comparing(
                                        field -> field.getAnnotation(FieldOrder.class).value()))
                        .toList();
        setFieldMenu(fields);
        setFieldMenu(fields);
    }

    private void setFieldMenu(List<Field> fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("Выберите поле для поиска:\n");
        for (int i = 0; i < fields.size(); i++)
            sb.append(
                    String.format(
                            "(%d) По параметру %s: %s\n",
                            i + 1,
                            fields.get(i).getType().getName().substring(10),
                            fields.get(i).getName()));
        sb.append("(0) Назад\n");
        menuSelectors = sb.toString();
    }
}
