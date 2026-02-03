import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Ui {
    private final String SEPARATOR = "-------------------------------------------";
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        String logo = " __  __          __   __\n"
                + "|  \\/  |   /\\    \\ \\ / /\n" 
                + "| \\  / |  /  \\    \\ V / \n" 
                + "| |\\/| | / /\\ \\    > <  \n" 
                + "| |  | |/ ____ \\  / . \\ \n"
                + "|_|  |_/_/    \\_\\/_/ \\_\\\n";
        System.out.println("Hello from\n" + logo);
        System.out.println(SEPARATOR);
        System.out.println("Hello! I'm Max\nWhat can I do for you?");
        System.out.println(SEPARATOR);
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
