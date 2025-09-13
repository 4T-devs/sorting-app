package com.ftdevs.sortingapp;

import java.util.Scanner;

public final class SorterMain {
    private SorterMain() {

    }

    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext();

        try (Scanner scanner = new Scanner(System.in)) {
            while (context.isExit()) {
                context.printMenu();
                context.setInput(scanner.nextLine());
                if (!context.handle())
                    context.printError();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("END");
    }
}
