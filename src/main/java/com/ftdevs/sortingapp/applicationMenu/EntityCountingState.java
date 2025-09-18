package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.IOSingleton;
import com.ftdevs.sortingapp.entities.FieldOrder;
import java.util.Arrays;
import java.util.Comparator;

public class EntityCountingState extends MenuInputState {

    @Override
    public boolean handle(ApplicationContext context) {
        int counter = 0;
        for (var item : context.getCollection())
            if (item.toString().equals(context.getInput().replace(' ', ','))) counter++;

        IOSingleton.getInstance()
                .printLine(String.format("Количество заданных объектов: %d", counter));
        context.setState(new MainMenuState());
        return true;
    }

    public EntityCountingState(ApplicationContext context) {
        menuSelectors = setEntityFields(context.getEntityType());
    }

    private String setEntityFields(Class<?> type) {
        var fields =
                Arrays.stream(type.getDeclaredFields())
                        .sorted(
                                Comparator.comparing(
                                        field -> field.getAnnotation(FieldOrder.class).value()))
                        .toList();

        StringBuilder sb = new StringBuilder();
        sb.append("Необходимо задать следующие параметры через пробел:\n");
        for (var field : fields)
            sb.append(
                    String.format(
                            "|%s: %s", field.getType().getName().substring(10), field.getName()));
        return sb.toString();
    }
}
