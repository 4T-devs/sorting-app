package com.ftdevs.sortingapp.entityCreators;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ftdevs.sortingapp.entities.Entity;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

final class FileReaderTest {

    private FileReaderTest() { }

    @Test
    void readFileTest() {
        String filePath = "File.txt";
        Path path = Paths.get(filePath);

        String input = "2,2.56,6.13\n18,34.2,10.23";

        try {
            Files.createFile(path);
            Files.write(path, input.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        FileReader reader = new FileReader();
        var result = reader.createEntities(filePath, Entity.EntityBuilder.class);
        assertTrue(Arrays.deepEquals(input.split("\n"), result));

        if (Files.exists(path))
            try {
                Files.delete(path);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }
}
