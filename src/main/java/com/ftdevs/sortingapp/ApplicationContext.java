package com.ftdevs.sortingapp;

import com.ftdevs.sortingapp.applicationMenu.MainMenuState;
import com.ftdevs.sortingapp.applicationMenu.MenuInputState;
import com.ftdevs.sortingapp.entities.Builder;
import com.ftdevs.sortingapp.entities.Entity;
import com.ftdevs.sortingapp.entityCreators.ICreationStrategy;
import com.ftdevs.sortingapp.sorting.ISortStrategy;

public class ApplicationContext {
    private MenuInputState state;
    private String input;
    private boolean exitFlag = true;

    private Object[] collection;

    private ISortStrategy sortStrategy;

    private ICreationStrategy creationStrategy;

    private Class<? extends Builder> entityType;

    public boolean handle() {
        return state.handle(this);
    }

    public void setState(MenuInputState state) {
        this.state = state;
    }

    public void setInput(String input) {
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

    public void setCollection(Object[] collection) {
        this.collection = collection;
    }

    public Object[] getCollection() {
        return collection;
    }

    public void setSortStrategy(ISortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public ISortStrategy getSortStrategy() {
        return sortStrategy;
    }

    public void setCreationStrategy(ICreationStrategy creationStrategy) {
        this.creationStrategy = creationStrategy;
    }

    public ICreationStrategy getCreationStrategy() {
        return creationStrategy;
    }

    public void setEntityType(Class<? extends Builder> entityType) {
        this.entityType = entityType;
    }

    public Class<? extends Builder> getEntityType() {
        return entityType;
    }

    public void printError() {
        System.out.println(state.getErrorMessage());
    }

    public ApplicationContext() {
        state = new MainMenuState();
        entityType = Entity.EntityBuilder.class;
    }

    public void printObjects() {
        try {
            for (var i : collection)
                System.out.println(i);
        } catch (Exception ex) {
            System.out.println("Список объектов пуст");
        }
    }

    public void sort() {
        collection = sortStrategy.sort(collection);
    }

    public void printMenu() {
        System.out.println(state.getMenu());
    }
}
