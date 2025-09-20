package com.ftdevs.sortingapp;

import com.ftdevs.sortingapp.applicationMenu.MainMenuState;
import com.ftdevs.sortingapp.applicationMenu.MenuInputState;
import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.entityCreators.ICreationStrategy;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.sorting.ISortStrategy;

public class ApplicationContext {
    private MenuInputState state;
    private String input;
    private boolean exitFlag = true;

    private CustomList<Product> collection;

    private ISortStrategy sortStrategy;

    private ICreationStrategy creationStrategy;

    private boolean isInputNeed = true;

    private boolean isSorted = false;

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

    public void setCollection(final CustomList<Product> collection) {
        this.collection = collection;
    }

    public CustomList<Product> getCollection() {
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
            isSorted = true;
        }
    }

    public void printMenu() {
        System.out.println(state.getMenu());
    }

    public void printHeader() {
        System.out.println("===| Приложение сортировки продуктов |===\n");
    } // \n для того, чтобы отделить шапку от меню

    public boolean isInputNeed() {
        return isInputNeed;
    }

    public void setInputNeed(final boolean isInputNeed) {
        this.isInputNeed = isInputNeed;
    }

    public void setUnsorted() {
        isSorted = false;
    }

    public boolean isSorted() {
        return isSorted;
    }
}
