package com.ftdevs.sortingapp.files;

import com.ftdevs.sortingapp.collections.CustomArrayList;
import com.ftdevs.sortingapp.collections.CustomList;
import com.ftdevs.sortingapp.model.Product;
import com.ftdevs.sortingapp.util.ProductValidator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class FileManager {

    private static final String INPUT_DIR = "data\\";
    private static final String OUTPUT_DIR = "results\\";

    private static final String[] PRODUCT_FIELDS = {"sku", "name", "price"};

    private FileManager() {}

    public static CustomList<Product> readFile(final String filename) {
        String pathString = String.format("%s%s.csv", INPUT_DIR, filename);
        Path path = Paths.get(pathString);

        Path dirPath = Paths.get(INPUT_DIR);
        if (Files.notExists(dirPath))
            try {
                Files.createDirectories(dirPath);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        if (Files.notExists(path))
            throw new IllegalArgumentException(String.format("Файл %s не существует", pathString));

        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(path);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if (lines.get(0).split(",").length != PRODUCT_FIELDS.length)
            throw new IllegalArgumentException("Файл содержит неверное количество полей");

        int[] head = checkHead(lines.get(0).split(","));
        CustomList<Product> products = new CustomArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            var buffer = lines.get(i).split(",");
            if (buffer.length != head.length)
                throw new IllegalArgumentException("Файл содержит неверное количество параметров");
            products.add(
                    ProductValidator.createProduct(
                            buffer[head[0]].replace("'", ""),
                            buffer[head[1]].replace("'", ""),
                            buffer[head[2]]));
        }

        return products;
    }

    public static void save(final CustomList<Product> products, final String filename) {
        String pathString = String.format("%s%s.csv", OUTPUT_DIR, filename);
        Path path = Paths.get(pathString);

        Path dirPath = Paths.get(OUTPUT_DIR);
        if (Files.notExists(dirPath))
            try {
                Files.createDirectories(dirPath);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        SaveMode mode = SaveMode.UNKNOWN;
        if (Files.exists(path)) {
            try {
                if (Files.readAttributes(path, BasicFileAttributes.class).size()
                        >= "sku,name,price".getBytes().length) mode = SaveMode.APPEND;
                else mode = SaveMode.ADD_HEAD;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else mode = SaveMode.FULL_CREATION;

        saveByMode(products, path, mode);
    }

    public static void saveSearchResult(final Object product, final int position) {
        var path = Paths.get(String.format("%ssearchResult.txt", OUTPUT_DIR));

        var output =
                String.format("Продукт: |%s| найден на позиции: %d", product.toString(), position)
                        .getBytes();
        if (Files.exists(path))
            try {
                Files.write(path, output, StandardOpenOption.APPEND);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        else
            try {
                Files.write(path, output, StandardOpenOption.CREATE);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

    private static int[] checkHead(final String... head) {
        int[] headIndexes = new int[3];

        for (int i = 0; i < PRODUCT_FIELDS.length; i++) {
            for (int j = 0; j < head.length; j++) {
                if (head[j].equals(PRODUCT_FIELDS[i])) {
                    headIndexes[i] = j;
                    break;
                }
            }
        }

        if (Arrays.stream(headIndexes).distinct().count() == PRODUCT_FIELDS.length)
            return headIndexes;
        else throw new IllegalArgumentException("Оишбка чтения заголовка");
    }

    private static void saveByMode(
            final CustomList<Product> products, final Path path, SaveMode mode) {
        switch (mode) {
            case APPEND -> {
                try {
                    for (var p : products)
                        Files.writeString(
                                path,
                                String.format(
                                        Locale.ENGLISH,
                                        "'%s','%s',%f\n",
                                        p.getSku(),
                                        p.getName(),
                                        p.getPrice()),
                                StandardOpenOption.APPEND);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            case ADD_HEAD -> {
                try {
                    Files.write(path, "sku,name,price\n".getBytes(), StandardOpenOption.APPEND);
                    saveByMode(products, path, SaveMode.APPEND);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            case FULL_CREATION -> {
                try {
                    Files.createFile(path);
                    saveByMode(products, path, SaveMode.ADD_HEAD);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            case UNKNOWN -> System.out.println("Неизвестная опция сохранения");
        }
    }
}
