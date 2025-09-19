package com.ftdevs.sortingapp;

import com.ftdevs.sortingapp.applicationMenu.MainMenuState;
import com.ftdevs.sortingapp.applicationMenu.MenuInputState;
import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.entityCreators.ICreationStrategy;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.sorting.ISortStrategy;
import java.lang.reflect.Field;

public class ApplicationContext {
    private MenuInputState state;
    private String input;
    private boolean exitFlag = true;

    private CustomArrayList<Product> collection;

    private ISortStrategy sortStrategy;

    private ICreationStrategy creationStrategy;

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

    public void setCollection(final CustomArrayList<Product> collection) {
        this.collection = collection;
    }

    public CustomArrayList<Product> getCollection() {
        return collection;
    }

    public void setSortStrategy(final ISortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public ISortStrategy getSortStrategy() {
        return sortStrategy;
    }

    public void setCreationStrategy(final ICreationStrategy creationStrategy) {
        this.creationStrategy = creationStrategy;
    }

    public ICreationStrategy getCreationStrategy() {
        return creationStrategy;
    }

    public void printError() {
        System.out.println(state.getErrorMessage());
    }

    public ApplicationContext() {
        state = new MainMenuState();
        collection = new CustomArrayList<>();
    }

    public void printObjects() {
        if (collection != null && collection.size() > 0)
            for (var i : collection) System.out.println(i.toString());
        else System.out.println("Список объектов пуст");
    }

    public void sort() {
        if (collection != null && collection.size() > 0 && sortStrategy != null) {
            // sortStrategy.sort(collection, Comparator.comparing();
            // Можно создать в контексте компаратор для каждого поля, и на ввод давать collection,
            // comparator
            System.out.println("[PH]");
        }
    }

    public void printMenu() {
        System.out.println(state.getMenu());
    }

    public void printHeader() {
        System.out.println("===| Приложение сортировки продуктов |===\n");
    } // \n для того, чтобы отделить шапку от меню

    public Field getSortField() {
        return sortField;
    }

    public void setSortField(Field sortField) {
        this.sortField = sortField;
    }
}
