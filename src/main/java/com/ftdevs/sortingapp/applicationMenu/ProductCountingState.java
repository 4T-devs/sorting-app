package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.validation.InputValidator;
import java.util.Scanner;

public class ProductCountingState extends MenuInputState {

    @Override
    public boolean handle(ApplicationContext context) {
        Product product = null;

        while (product == null) {
            try {
                product = getProductForCounting();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        int counter = 0;
        for (var item : context.getCollection()) {
            if (product.compareTo(item) == 0) counter++;
        }

        System.out.println(String.format("Количество заданных объектов: %d", counter));
        context.setState(new MainMenuState());
        return true;
    }

    public ProductCountingState() {
        menuSelectors = "Перед подсчетом необходимо создать объект\nдля продолжения введите пробел";
    }

    private Product getProductForCounting() {
        Scanner scanner = new Scanner(System.in);
        var builder = new Product.Builder();

        System.out.println("Введите артикул:");
        builder.sku(scanner.nextLine().trim());

        System.out.println("Введите название:");
        builder.name(scanner.nextLine().trim());

        System.out.println("Введите цену:");
        builder.price(InputValidator.tryParseDouble(scanner.nextLine().trim()));

        return builder.build();
    }
}
