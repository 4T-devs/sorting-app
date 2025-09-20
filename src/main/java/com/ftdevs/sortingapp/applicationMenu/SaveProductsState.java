package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.files.FileManager;

public class SaveProductsState extends MenuInputState {

    @Override
    public boolean handle(ApplicationContext context) {
        try {
            FileManager.save(context.getCollection(), context.getInput());
        } catch (Exception ex) {
            errorMessage = String.format("Возникла ошибка при сохранении:\n%s", ex.getMessage());
            return false;
        }

        context.setState(new MainMenuState());
        return true;
    }

    public SaveProductsState() {
        menuSelectors = "Введите название файла";
    }
}
