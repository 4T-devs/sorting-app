package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.util.ProductValidator;
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
            if (product.getSku().equals(item.getSku())
                    && product.getName().equals(item.getName())
                    && product.getPrice() == item.getPrice()) counter++;
        }

        System.out.println(String.format("Количество заданных объектов: %d", counter));
        context.setState(new MainMenuState());
        context.setInputNeed(true);
        return true;
    }

    public ProductCountingState() {
        menuSelectors = "Перед подсчетом необходимо создать объект";
    }

    private Product getProductForCounting() {
        Scanner scanner = new Scanner(System.in);
        String sku;
        String name;
        String price;

        System.out.println("Введите артикул:");
        sku = scanner.nextLine().trim();

        System.out.println("Введите название:");
        name = scanner.nextLine().trim();

        System.out.println("Введите цену:");
        price = scanner.nextLine().trim();

        return ProductValidator.createProduct(sku, name, price);
    }
}
