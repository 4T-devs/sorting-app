package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;

public class ProductConfigState extends MenuInputState {
    @Override
    public boolean handle(ApplicationContext context) {
        try {
            context.setCollection(context.getCreationStrategy().createProducts(context.getInput()));
        } catch (Exception ex) {
            errorMessage =
                    String.format("Возникла ошибка при создании продуктов:\n%s", ex.getMessage());
            return false;
        }

        context.setState(new MainMenuState());
        context.setInputNeed(true);
        context.setUnsorted();
        return true;
    }

    public ProductConfigState(String message) {
        menuSelectors = message;
    }
}
