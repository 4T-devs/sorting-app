package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.entities.Builder;
import com.ftdevs.sortingapp.entities.FieldOrder;
import com.ftdevs.sortingapp.validation.InputValidator;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class RandomCreator implements ICreationStrategy {
    private static int MAX_VALUE = 100;
    private static int MIN_VALUE = 1;

    private final String NAME_INTEGER = "java.lang.Integer";
    private final String NAME_FLOAT = "java.lang.Float";
    private final String NAME_DOUBLE = "java.lang.Double";
    private final String NAME_LONG = "java.lang.Long";
    private final String NAME_STRING = "java.lang.String";
    @Override
    public String[] createEntities(String input, Class<? extends Builder> type) {
        Integer count = InputValidator.tryParseInteger(input);
        if (count == null)
            return new String[0];

        List<Field> fields = Arrays.stream(type.getDeclaredFields()).
                sorted(Comparator.comparing(field -> field.getAnnotation(FieldOrder.class).value()))
                .toList();
        String[] result = new String[count];


        for (int i = 0; i < count; i++) {
            result[i] = "";
            for (int j = 0; j < fields.size(); j++)
                result[i] += j < fields.size() - 1 ?
                        createValue(fields.get(j).getType().getName()) + "," :
                        createValue(fields.get(j).getType().getName());
        }



        return result;
    }

    @Override
    public String getMessage() {
        return "Введите количество объектов для генерации";
    }

    private String createValue(String typeName) {
        Random r = new Random();
        String result = "";

        switch (typeName) {
            case NAME_INTEGER -> result = String.valueOf(r.nextInt(MIN_VALUE, MAX_VALUE + 1));
            case NAME_FLOAT -> result = String.valueOf(r.nextFloat(MIN_VALUE, MAX_VALUE + 1));
            case NAME_DOUBLE -> result = String.valueOf(r.nextDouble(MIN_VALUE, MAX_VALUE + 1));
            case NAME_LONG -> result = String.valueOf(r.nextLong(MIN_VALUE, MAX_VALUE + 1));
            case NAME_STRING -> {
                byte[] array = new byte[MAX_VALUE / 2];
                r.nextBytes(array);
                result = new String(array, Charset.forName("UTF-8"));
            }
            default -> {

            }
        }
        return result;
    }
}
