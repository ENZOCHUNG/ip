package max;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import max.command.Command;

/**
 * Represents the main entry point for the Max chatbot application.
 * This class coordinates the user interface, storage, and logic to manage tasks.
 * It supports both CLI and JavaFX GUI modes.
 */
public class Max extends Application {
    private static final String DEFAULT_FILE_PATH = "max/data/Max.txt";
    private static final String BOT_NAME = "Max";

    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private String commandType;
    private boolean isExit = false;

    /**
     * Initialises a Max chatbot instance with a specific file path for data storage.
     *
     * @param filePath The file path where task data is saved and loaded from.
     */
    public Max(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            taskList = storage.load();
        } catch (Exception e) {
            ui.showLoadingError();
            e.printStackTrace();
            taskList = new TaskList();
        }
    }
    /**
     * Initialises a Max chatbot instance using the default file path.
     */
    public Max() {
        this(DEFAULT_FILE_PATH);
    }
    /**
     * Checks if the application has received a command to terminate.
     *
     * @return true if an exit command has been processed, false otherwise.
     */
    public boolean isExit() {
        return this.isExit;
    }
    /**
     * Initialises and displays the JavaFX stage for the graphical user interface.
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            FXMLLoader fxmlLoader = new FXMLLoader(Max.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setMax(this);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Processes a raw user input string, executes the corresponding command,
     * and returns the response message.
     *
     * @param input The raw input string from the user.
     * @return A string representing the chatbot's response or an error message.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            isExit = c.isExit();
            String response = c.execute(taskList, storage);
            isExit = c.isExit();
            return response;
        } catch (MaxException e) {
            isExit = false;
            return "Error: " + e.getMessage();
        }
    }

    public String getCommandType() {
        return commandType;
    }

    /**
     * Returns the name of the chatbot.
     *
     * @return A string representing the chatbot's constant name.
     */
    public String getName() {
        return this.BOT_NAME;
    }

    /**
     * Returns the greeting text of the chatbot
     */
    public void greet() {
        ui.showLine();
        System.out.println("Hello! I'm " + this.getName());
        System.out.println("What can I do for you?");
        ui.showLine();
    }

    /**
     * Returns the exit text of the chatbot
     */
    public void exit() {
        System.out.println("Bye. Hope to see you again soon!");
        ui.showLine();
    }

    /**
     * Starts the main execution loop for the CLI version of the chatbot.
     * Processes user commands and updates storage until the exit command is received.
     */
    public void run() {
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();

                Command c = Parser.parse(fullCommand);
                String response = c.execute(taskList, storage);
                ui.showMessage(response);

                isExit = c.isExit();
            } catch (MaxException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }
    /**
     * Main entry point for starting the application in CLI mode.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Max("max/data/Max.txt").run();
    }
}
