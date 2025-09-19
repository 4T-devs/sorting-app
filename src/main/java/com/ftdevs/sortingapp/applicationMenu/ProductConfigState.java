package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;

public class ProductConfigState extends MenuInputState {
    @Override
    public boolean handle(ApplicationContext context) {
        context.setCollection(context.getCreationStrategy().createProducts(context.getInput()));

        context.setState(new MainMenuState());
        return true;
    }

    public ProductConfigState(String message) {
        menuSelectors = message;
    }
}
