package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.validation.InputValidator;
import java.util.Scanner;

public class HandInput implements ICreationStrategy {

    @Override
    public CustomArrayList<Product> createProducts(String input) {
        if ("stop".equals(input)) return new CustomArrayList<>();

        return handleCreation();
    }

    @Override
    public String getMessage() {
        return "Ручное создание объектов. Для выхода введите 'stop'";
    }

    private CustomArrayList<Product> handleCreation() {
        String exitWord = "stop";
        Scanner scanner = new Scanner(System.in);
        CustomArrayList<Product> collection = new CustomArrayList<>();

        while (true) {
            var builder = new Product.Builder();
            String input = scanner.nextLine();
            System.out.println("Введите артикул");
            if (input.equals(exitWord)) {
                break;
            } else builder.sku(input);
            System.out.println("Введите название");
            input = scanner.nextLine();
            if (input.equals(exitWord)) {
                break;
            } else builder.name(input);
            System.out.println("Введите цену");
            input = scanner.nextLine();
            if (input.equals(exitWord)) {
                break;
            } else builder.price(InputValidator.tryParseDouble(input));
            try {
                collection.add(builder.build());
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return collection;
    }
}
