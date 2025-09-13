package com.ftdevs.sortingapp;

import java.util.Scanner;

public final class SorterMain {
    private SorterMain() {}

    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext();

        Scanner scanner = new Scanner(System.in);
        while (context.exitFlag){
            context.printMenu();
            context.input = scanner.nextLine();
            if(!context.handle())
                context.printError();
        }
        scanner.close();
    }
}
