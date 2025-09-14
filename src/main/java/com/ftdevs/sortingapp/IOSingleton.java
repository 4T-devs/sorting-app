package com.ftdevs.sortingapp;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public final class IOSingleton {

    private static IOSingleton instance;

    private Scanner scanner;

    private PrintStream printStream;

    private IOSingleton() {}

    public static IOSingleton getInstance() {
        if (instance == null) instance = new IOSingleton();
        return instance;
    }

    public void setInput(InputStream stream) {
        scanner = new Scanner(stream);
    }

    public void setOutput(PrintStream stream) {
        printStream = stream;
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public void printLine(String input) {
        printStream.println(input);
    }
}
