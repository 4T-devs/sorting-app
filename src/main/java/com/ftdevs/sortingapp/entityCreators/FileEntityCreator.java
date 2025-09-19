package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.model.Product;

public class FileEntityCreator implements ICreationStrategy {

    @Override
    public CustomArrayList<Product> createProducts(String input) {
        // TODO Переработка метода
        /*
        Path filePath = Paths.get(input);
        if (!Files.exists(filePath)) return new String[0];
        List<String> values = new ArrayList<>();

        try {
            values = Files.readAllLines(filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return values.toArray(new String[values.size()]);

        */
        return new CustomArrayList<>();
    }

    @Override
    public String getMessage() {
        return "Введите название файла";
    }
}
