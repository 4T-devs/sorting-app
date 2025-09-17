package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.IOSingleton;
import com.ftdevs.sortingapp.entities.Builder;
import com.ftdevs.sortingapp.entities.FieldOrder;
import com.ftdevs.sortingapp.validation.InputValidator;
import java.util.Arrays;
import java.util.Comparator;

public class HandInput implements ICreationStrategy {

    @Override
    public String[] createEntities(String input, Class<? extends Builder> type) {
        Integer count = InputValidator.tryParseInteger(input);
        if (count == null) return new String[0];
        count = Math.max(0, count);

        String[] result = new String[count];
        printEntityFields(type);
        try {
            for (int i = 0; i < count; ) {
                String buffer = IOSingleton.getInstance().readLine();
                if (buffer.split(" ").length == type.getDeclaredFields().length) {
                    result[i] = buffer;
                    i++;
                } else {
                    IOSingleton.getInstance().printLine("Введено неверное количество аргументов");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public String getMessage() {
        return "Введите количество создаваемых объектов";
    }

    private void printEntityFields(Class<?> type) {
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
        IOSingleton.getInstance().printLine(sb.toString());
    }
}
