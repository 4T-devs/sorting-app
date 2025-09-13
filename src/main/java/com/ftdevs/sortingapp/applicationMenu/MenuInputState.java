package org.example.applicationMenu;

import org.example.ApplicationContext;

public abstract class MenuInputState {
    protected String errorMessage;

    protected String menuSelectors;
    public abstract boolean handle(ApplicationContext context);

    public String getErrorMessage(){
        return errorMessage;
    }

    public String getMenu(){
        return menuSelectors;
    }
}
