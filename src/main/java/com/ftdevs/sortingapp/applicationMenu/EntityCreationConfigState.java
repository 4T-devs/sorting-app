package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.entityCreators.HandInput;

public class EntityCreationConfigState extends MenuInputState {
    @Override
    public boolean handle(ApplicationContext context) {
        var buffer =
                context.getCreationStrategy()
                        .createEntities(
                                context.getInput(),
                                context.getEntityType()); // Создание данных для сущностей с
        // помощью выбранной стратегии
        var collection = new Object[buffer.length];
        try { // Ввод данных в билдер для создания объектов
            var builder =
                    context.getEntityType()
                            .getDeclaredConstructor()
                            .newInstance(); // Создание экземпляра билдера выбранной сущности
            for (int i = 0;
                    i < buffer.length;
                    i++) { // Для каждой строки данных вызывается метод build автобилдера
                collection[i] =
                        context.getEntityType()
                                .getMethod("build", String.class, String.class)
                                .invoke(builder, buffer[i], selectSeparator(context));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        context.setState(new MainMenuState());
        context.setCollection(collection);
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
