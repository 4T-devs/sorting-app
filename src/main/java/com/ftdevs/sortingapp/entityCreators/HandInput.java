package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.util.ProductValidator;
import java.util.Scanner;

public class HandInput implements ICreationStrategy {

    @Override
    public CustomList<Product> createProducts(String input) {
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
            String input = scanner.nextLine().trim();
            String sku;
            String name;
            String price;

            System.out.println("Введите артикул");
            if (input.equals(exitWord)) {
                break;
            } else sku = input;
            System.out.println("Введите название");
            input = scanner.nextLine().trim();
            if (input.equals(exitWord)) {
                break;
            } else name = input;
            System.out.println("Введите цену");
            input = scanner.nextLine().trim();
            if (input.equals(exitWord)) {
                break;
            } else price = input;
            try {
                collection.add(ProductValidator.createProduct(sku, name, price));
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return collection;
    }
}
