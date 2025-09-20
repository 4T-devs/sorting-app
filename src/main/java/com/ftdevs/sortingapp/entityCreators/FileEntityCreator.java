package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.files.FileManager;
import com.ftdevs.sortingapp.model.Product;

public class FileEntityCreator implements ICreationStrategy {

    @Override
    public CustomList<Product> createProducts(String input) {
        return FileManager.readFile(input);
    }

    @Override
    public String getMessage() {
        return "Введите название файла";
    }
}
