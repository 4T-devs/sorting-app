package com.ftdevs.sortingapp;

import static org.junit.jupiter.api.Assertions.*;

import com.ftdevs.sortingapp.applicationMenu.ProductSearchState;
import java.io.*;
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

        assertNotSame(menu1, menu2, String.format("%s\nnot same as\n%s", menu1, menu2));
    }

    @Test
    void applicationExitTest() {
        System.setOut(System.out);
        final ApplicationContext context = new ApplicationContext();

        final String input = "0";

        context.setInput(input);
        context.handle();

        assertTrue(!context.isExit(), "Exit successful");
    }

    @Test
    void applicationAddItemsTest() {
        System.setOut(System.out);
        final ApplicationContext context = new ApplicationContext();
        final String input =
                "1 3 1"; // 1 Создание объектов, 3 - Ручной ввод, 1 - Количество объектов
        final var commands = input.split(" ");
        System.setIn(new ByteArrayInputStream("article\nname\n9.99\nstop".getBytes()));

        for (String command : commands) {
            context.setInput(command);
            context.handle();
        }

        assertTrue(context.getCollection().size() > 0, "Collection is empty");

        context.printObjects();
    }

    @Test
    void applicationMenuWalkerTest() { // Тест с обходом всего меню
        var originalInput = System.out;
        System.setOut(originalInput);

        final ApplicationContext context = new ApplicationContext();
        final String input = "1_0_2_0_3_4_0_5_6_7_ _0";
        System.setIn(new ByteArrayInputStream("article\nproduct\n3.14".getBytes()));

        for (var c : input.split("_")) {
            context.printHeader();
            context.printMenu();
            System.out.println(c);
            context.setInput(c);
            context.handle();
        }
        assertTrue(!context.isExit(), "Application menu successful walked");
    }
}
