package com.ftdevs.sortingapp;

import static org.junit.jupiter.api.Assertions.*;

import com.ftdevs.sortingapp.applicationMenu.ProductSearchState;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

final class ApplicationContextTest {

    private ApplicationContextTest() {}

    @Test
    void changeContextState() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outputStream));

        final ApplicationContext context =
                new ApplicationContext(); // По умолчанию в контексте задается состояние главного
        // меню
        context.printMenu();

        final String menu1 = outputStream.toString();
        outputStream.reset();

        context.setState(new ProductSearchState());
        context.printMenu();
        final String menu2 = outputStream.toString();

        System.out.println(menu1);
        System.out.println(menu2);

        assertNotSame(menu1, menu2, String.format("%s\n same as\n%s", menu1, menu2));
    }

    @Test
    void applicationExitTest() {
        System.setOut(System.out);
        final ApplicationContext context = new ApplicationContext();

        final String input = "0";

        context.setInput(input);
        context.handle();

        assertTrue(!context.isExit(), "Exit failed");
    }

    @Test
    void applicationAddItemsTest() {
        System.setOut(System.out);
        final ApplicationContext context = new ApplicationContext();
        final String input = "1 3 0"; // 1 Создание объектов, 3 - Ручной ввод, 0 - Выход
        final var commands = input.split(" ");
        System.setIn(new ByteArrayInputStream("GLT-648/7742\nПылесос\n9.99\nstop".getBytes()));

        int commandHead = 0;
        while (commandHead < commands.length) {
            context.printHeader();
            context.printMenu();
            if (context.isInputNeed()) {
                System.out.println(commands[commandHead]);
                context.setInput(commands[commandHead]);
                commandHead++;
            }
            if (!context.handle()) context.printError();
        }
        System.out.println(context.getCollection().get(0));

        assertTrue(context.getCollection().size() > 0, "Collection is empty");

        context.printObjects();
    }

    @Test
    void applicationMenuWalkerTest() { // Тест с обходом всего меню
        var originalInput = System.out;
        System.setOut(originalInput);

        final ApplicationContext context = new ApplicationContext();
        final String input = "1 0 2 0 3 4 5 6 7 file 8 0";
        System.setIn(new ByteArrayInputStream("AFE-957/6405\nНаушники\n3.14".getBytes()));

        int commandHead = 0;
        String[] commands = input.split(" ");
        while (commandHead < commands.length) {
            context.printHeader();
            context.printMenu();
            if (context.isInputNeed()) {
                System.out.println(commands[commandHead]);
                context.setInput(commands[commandHead]);
                commandHead++;
            }
            if (!context.handle()) context.printError();
        }

        assertTrue(!context.isExit(), "Application has not been shutdown");
    }

    @Test
    void applicationGenerateAndSaveTest() {
        var originalInput = System.out;
        System.setOut(originalInput);

        final ApplicationContext context = new ApplicationContext();
        final String input = "1 2 10 5 7 file";

        for (var i : input.split(" ")) {
            context.printHeader();
            context.printMenu();
            System.out.println(i);
            context.setInput(i);
            if (!context.handle()) context.printError();
        }

        assertTrue(
                context.getCollection().size() > 0 && Files.exists(Paths.get("results/file.csv")),
                "Ошибка при создании или сохранении продуктов");
    }

    /*
    @Test
    void applicationReadFile() {
        var originalInput = System.out;
        System.setOut(originalInput);

        final ApplicationContext context = new ApplicationContext();
        final String input = "1 1 file 5";

        for (var i : input.split(" ")) {
            context.printHeader();
            context.printMenu();
            System.out.println(i);
            context.setInput(i);
            if (!context.handle()) context.printError();
        }

        assertTrue(context.getCollection().size() > 0, "Expected collection size must be > 0");
    }
     */
}
