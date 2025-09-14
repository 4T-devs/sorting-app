package com.ftdevs.sortingapp;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import com.ftdevs.sortingapp.applicationMenu.OptionMenuState;
import java.io.*;
import org.junit.jupiter.api.Test;

final class ApplicationContextTest {

    private ApplicationContextTest() { }

    @Test
    void changeContextState() {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outputStream));
        IOSingleton.getInstance().setOutput(new PrintStream(outputStream));

        final ApplicationContext context =
                new ApplicationContext(); // По умолчанию в контексте задается состояние главного
        // меню
        context.printMenu();

        final String menu1 = outputStream.toString();
        outputStream.reset();

        context.setState(new OptionMenuState());
        context.printMenu();
        final String menu2 = outputStream.toString();

        IOSingleton.getInstance().printLine(menu1);
        IOSingleton.getInstance().printLine(menu2);

        assertNotSame(menu1, menu2, String.format("%s\nnot same as\n%s", menu1, menu2));
    }

    @Test
    void applicationExitTest() {
        final ApplicationContext context = new ApplicationContext();

        final String input = "5";

        context.setInput(input);
        context.handle();

        assertTrue(!context.isExit(), "Exit successful");
    }

    @Test
    void applicationAddItemsTest() {
        IOSingleton.getInstance().setOutput(System.out);
        final ApplicationContext context = new ApplicationContext();
        final String input = "2 2 10";
        final var commands = input.split(" ");

        for (String command : commands) {
            context.setInput(command);
            context.handle();
        }

        assertTrue(context.getCollection().length > 0, "Collection has elements");

        context.printObjects();
    }
}
