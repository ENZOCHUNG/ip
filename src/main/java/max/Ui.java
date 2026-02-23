package max;

import java.util.Scanner;

/**
 * This class displays messages and errors
 */
public class Ui {
    private static final String SEPARATOR = "-------------------------------------------";
    private final Scanner scanner = new Scanner(System.in);

    public String getWelcomeMessage() {
        return "Hello! I'm Max.\nWhat can I do for you?";
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println(SEPARATOR);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public void showLoadingError() {
        System.out.println("Failed to load tasks from file. Creating a new list.");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
