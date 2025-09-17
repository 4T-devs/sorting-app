package com.ftdevs.sortingapp;

import com.ftdevs.sortingapp.applicationMenu.MainMenuState;
import com.ftdevs.sortingapp.applicationMenu.MenuInputState;
import com.ftdevs.sortingapp.entities.Builder;
import com.ftdevs.sortingapp.entities.Entity;
import com.ftdevs.sortingapp.entityCreators.ICreationStrategy;
import com.ftdevs.sortingapp.sorting.ISortStrategyPlaceholder;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ApplicationContext {
    private MenuInputState state;
    private String input;
    private boolean exitFlag = true;

    private Object[] collection;

    private ISortStrategyPlaceholder sortStrategy;

    private ICreationStrategy creationStrategy;

    private Class<? extends Builder> entityType;

    private Field sortField;

    public boolean handle() {
        return state.handle(this);
    }

    public void setState(final MenuInputState state) {
        this.state = state;
    }

    public void setInput(final String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public boolean isExit() {
        return exitFlag;
    }

    public void exit() {
        exitFlag = false;
    }

    public void setCollection(final Object... collection) {
        this.collection = Arrays.copyOf(collection, collection.length);
    }

    public Object[] getCollection() {
        return Arrays.copyOf(collection, collection.length);
    }

    public void setSortStrategy(final ISortStrategyPlaceholder sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public ISortStrategyPlaceholder getSortStrategy() {
        return sortStrategy;
    }

    public void setCreationStrategy(final ICreationStrategy creationStrategy) {
        this.creationStrategy = creationStrategy;
    }

    public ICreationStrategy getCreationStrategy() {
        return creationStrategy;
    }

    public void setEntityType(final Class<? extends Builder> entityType) {
        this.entityType = entityType;
    }

    public Class<? extends Builder> getEntityType() {
        return entityType;
    }

    public void printError() {
        IOSingleton.getInstance().printLine(state.getErrorMessage());
    }

    public ApplicationContext() {
        state = new MainMenuState();
        collection = new Object[0];
        entityType = Entity.EntityBuilder.class;
    }

    public void printObjects() {
        if (collection != null && collection.length > 0)
            for (var i : collection) IOSingleton.getInstance().printLine(i.toString());
        else IOSingleton.getInstance().printLine("Список объектов пуст");
    }

    public void sort() {
        if (collection != null && collection.length > 0) collection = sortStrategy.sort(collection);
    }

    public void printMenu() {
        IOSingleton.getInstance().printLine(state.getMenu());
    }

    public void printHeader() {
        IOSingleton.getInstance().printLine("===| Приложение сортировки продуктов |===\n");
    }

    public Field getSortField() {
        return sortField;
    }

    public void setSortField(Field sortField) {
        this.sortField = sortField;
    }
}
