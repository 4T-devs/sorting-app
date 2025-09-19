package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.model.Product;

public interface ICreationStrategy {
    CustomArrayList<Product> createProducts(String input);

    String getMessage();
}
