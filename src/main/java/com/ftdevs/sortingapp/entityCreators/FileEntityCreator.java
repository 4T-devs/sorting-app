package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.entities.Builder;

public class FileEntityCreator implements ICreationStrategy {

    @Override
    public String[] createEntities(String input, Class<? extends Builder> type) {
        // TODO Переработка метода
        /*
        Path filePath = Paths.get(input);
        if (!Files.exists(filePath)) return new String[0];
        List<String> values = new ArrayList<>();

        try {
            values = Files.readAllLines(filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return values.toArray(new String[values.size()]);

        */
        return new String[0];
    }

    @Override
    public String getMessage() {
        return "Введите путь к файлу формата .csv";
    }
}
