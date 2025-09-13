package org.example;

import java.util.Scanner;

public class Main {
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