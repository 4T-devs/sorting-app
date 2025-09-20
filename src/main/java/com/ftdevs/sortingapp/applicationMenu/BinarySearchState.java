package com.ftdevs.sortingapp.applicationMenu;

import com.ftdevs.sortingapp.ApplicationContext;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.files.FileManager;
import com.ftdevs.sortingapp.searchui.SearchService;
import com.ftdevs.sortingapp.validation.InputValidator;
import java.util.Optional;

public class BinarySearchState extends MenuInputState {

    private final int option;

    @Override
    public boolean handle(ApplicationContext context) {
        Optional<Integer> idxOpt;
        switch (option) {
            case 1 -> idxOpt = SearchService.findBySku(context.getCollection(), context.getInput());
            case 2 ->
                    idxOpt = SearchService.findByName(context.getCollection(), context.getInput());
            case 3 -> {
                Double input = InputValidator.tryParseDouble(context.getInput());
                if (input == null) {
                    errorMessage = "Введено неккоректное число";
                    return false;
                }
                idxOpt = SearchService.findByPrice(context.getCollection(), input);
            }
            default -> {
                errorMessage = "Неверная опция";
                return false;
            }
        }
        printResult(context.getCollection(), idxOpt);
        context.setState(new ProductSearchState());
        return true;
    }

    public BinarySearchState(int option) {
        this.option = option;

        switch (option) {
            case 1 -> menuSelectors = "Введите артикул:";
            case 2 -> menuSelectors = "Введите название:";
            case 3 -> menuSelectors = "Введите цену:";
            default -> menuSelectors = "Неизвестная опция";
        }
    }

    private static <T> void printResult(
            final CustomList<T> products, final Optional<Integer> idxOpt) {
        if (idxOpt.isPresent()) {
            final int idx = idxOpt.get();
            FileManager.saveSearchResult(products.get(idx), idx);
            System.out.println("Найден объект: " + products.get(idx));
        } else {
            System.out.println("Элемент не найден.");
        }
    }
}
