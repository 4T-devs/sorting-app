package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.entityCreators.HandInput;

public class EntityCreationConfigState extends MenuInputState {
    @Override
    public boolean handle(ApplicationContext context) {
        var buffer = context.getCreationStrategy().createEntities(context.getInput(), context.getEntityType());
        var collection = new Object[buffer.length];
        try {
            var builder = context.getEntityType().getDeclaredConstructor().newInstance();
            for (int i = 0; i < buffer.length; i++)
                collection[i] = context.getEntityType()
                        .getMethod("build", String.class, String.class)
                        .invoke(builder, buffer[i], selectSeparator(context));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        context.setState(new MainMenuState());
        return true;
    }

    public EntityCreationConfigState(String message) {
        menuSelectors = message;
    }

    private String selectSeparator(ApplicationContext context) {
        if (context.getCreationStrategy().getClass().getName().equals(HandInput.class.getName()))
            return " ";
        else return ",";
    }
}
