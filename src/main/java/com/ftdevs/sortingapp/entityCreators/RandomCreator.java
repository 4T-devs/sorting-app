package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.util.ProductGenerator;
import com.ftdevs.sortingapp.validation.InputValidator;

public class RandomCreator implements ICreationStrategy {
    @Override
    public CustomList<Product> createProducts(String input) {
        Integer count = InputValidator.tryParseInteger(input);
        if (count == null) {
            System.out.println("Введены некорректные данные");
            return new CustomArrayList<>();
        }

        return ProductGenerator.generateProducts(Math.max(0, count));
    }

    @Override
    public String getMessage() {
        return "Введите количество генерируемых продуктов";
    }
}
