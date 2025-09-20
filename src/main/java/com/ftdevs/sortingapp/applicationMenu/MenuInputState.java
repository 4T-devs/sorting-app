package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;

public abstract class MenuInputState {
    protected String errorMessage;

    protected String menuSelectors;

    public abstract boolean handle(ApplicationContext context);

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getMenu() {
        return menuSelectors;
    }
}
