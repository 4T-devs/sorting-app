package org.example.entityCreators;

import org.example.entities.Builder;
import org.example.validation.InputValidator;

import java.util.Scanner;

public class HandInput implements ICreationStrategy {
    @Override
    public String[] createEntities(String input, Class<? extends Builder> type) {
        Integer count = InputValidator.tryParseInteger(input);
        if(count == null)
            return new String[0];

        String[] result = new String[count];
        printEntityFields(type);
        Scanner sc = new Scanner(System.in);
        for(int i = 0; i < count;){
            String buffer = sc.nextLine();
            if(buffer.split(" ").length == type.getDeclaredFields().length){
                result[i] = buffer;
                i++;
            } else {
                System.out.println("Введено неверное количество аргументов");
            }
        }

        return result;
    }

    @Override
    public String getMessage() {
        return "Введите количество создаваемых объектов";
    }

    private void printEntityFields(Class<?> type){
        var fields = type.getDeclaredFields();

        StringBuilder sb = new StringBuilder();
        sb.append("Необходимо задать следующие параметры через пробел:\n");
        for(var field : fields)
            sb.append(String.format("|%s: %s",
                    field.getType().getName().substring(10),
                    field.getName()));
        System.out.println(sb.toString());
    }
}
