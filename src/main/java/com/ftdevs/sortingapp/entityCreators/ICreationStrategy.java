package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.model.Product;

public interface ICreationStrategy {
    CustomList<Product> createProducts(String input);

    String getMessage();
}
