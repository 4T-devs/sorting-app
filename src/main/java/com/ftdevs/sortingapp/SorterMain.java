package com.ftdevs.sortingapp;

public final class SorterMain {
    private SorterMain() {}

    public static void main(String[] args) {
        final ApplicationContext context = new ApplicationContext();

        IOSingleton.getInstance().setOutput(System.out);
        IOSingleton.getInstance().setInput(System.in);

        try {
            while (context.isExit()) {
                context.printMenu();
                context.setInput(IOSingleton.getInstance().readLine());
                if (!context.handle()) context.printError();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        IOSingleton.getInstance().printLine("END");
    }
}
