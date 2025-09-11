package org.example;

import org.example.applicationMenu.MainMenuState;
import org.example.applicationMenu.MenuInputState;
import org.example.sorting.ISortStrategy;

public class ApplicationContext {
    private MenuInputState state;
    public String input;
    public boolean exitFlag = true;

    public Object[] collection;

    public ISortStrategy sortStrategy;

    public boolean hold(){
        return state.hold(this);
    }

    public void setState(MenuInputState state){
        this.state = state;
    }

    public void printError(){
        System.out.println(state.getErrorMessage());
    }

    public ApplicationContext(){
        state = new MainMenuState();

    }

    public void printObjects(){
        try {
            for(var i : collection)
                System.out.println(i);
        } catch (Exception ex){
            System.out.println("Список объектов пуст");
        }
    }

    public void sort(){
        collection = sortStrategy.sort(collection);
    }

    public void printMenu(){
        System.out.println(state.getMenu());
    }
}
