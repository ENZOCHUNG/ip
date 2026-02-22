package max;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.Scanner;
/**
 * This class displays messages and errors
 */
public class Ui {
    private static final String SEPARATOR = "-------------------------------------------";
    private final Scanner scanner = new Scanner(System.in);
    /**
     * Prints the logo of the chatbot
     */
    public String getWelcomeMessage() {
        return "Hello! I'm Max.\nWhat can I do for you?";
    }
    /**
     * Creates the input by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }
    /**
     * This method prints a single line
     */
    public void showLine() {
        System.out.println(SEPARATOR);
    }
    /**
     * This method prints error message
     * @param message Prints this error message
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }
    /**
     * This method prints loading error message
     */
    public void showLoadingError() {
        System.out.println("Failed to load tasks from file. Creating a new list.");
    }
    /**
     * This method prints a message
     * @param message Prints this message
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
