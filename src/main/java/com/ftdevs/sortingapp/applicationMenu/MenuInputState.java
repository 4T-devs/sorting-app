package org.example.applicationMenu;

import org.example.ApplicationContext;

public abstract class MenuInputState {
    protected String errorMessage;

    protected String menuSelectors;
    public abstract boolean hold(ApplicationContext context);

    public String getErrorMessage(){
        return errorMessage;
    }

    public String getMenu(){
        return menuSelectors;
    }
}
