package com.ftdevs.sortingapp.entityCreators;

import com.ftdevs.sortingapp.entities.Builder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader implements ICreationStrategy {

    @Override
    public String[] createEntities(String input, Class<? extends Builder> type) {
        Path filePath = Paths.get(input);
        if (!Files.exists(filePath)) return new String[0];
        List<String> values = new ArrayList<>();

        try {
            values = Files.readAllLines(filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return values.toArray(new String[values.size()]);
    }

    @Override
    public String getMessage() {
        return "Введите путь к файлу формата .csv";
    }
}
