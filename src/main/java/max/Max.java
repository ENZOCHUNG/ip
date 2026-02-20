package max;

import max.task.Task;
import max.task.ToDo;
import max.task.Event;
import max.TaskList;
import max.task.Deadline;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.PrintWriter;
import java.io.File;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
//import javafx.scene.web.HTMLEditorSkin.Command;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.fxml.FXMLLoader;

public class Max extends Application {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private String input;
    private final String NAME = "MAX";
    private static final String DEFAULT_FILE_PATH = "max/data/Max.txt";
    private String commandType;

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

    public Max() {
        this(DEFAULT_FILE_PATH);
    }

    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            FXMLLoader fxmlLoader = new FXMLLoader(Max.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setDuke(this);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        String response;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
        try {
            if (input.equalsIgnoreCase("list")) {
                response = "Here are the tasks in your list:\n" + taskList.toString();
            } 
            
            else if (input.startsWith("mark ")) {
                int index = Integer.parseInt(input.substring(5).trim()) - 1;
                taskList.markTask(index);
                storage.save();
                response = "Nice! I've marked this task as done:\n" + taskList.getTask(index).toString();
            } 

            else if (input.startsWith("unmark ")) {
                String num = input.substring(7).trim();
                int index = Integer.parseInt(num) - 1;

                if (index < 0 || index >= taskList.getTaskLength()) {
                    throw new IndexOutOfBoundsException();
                }
                
                taskList.unmarkTask(index);
                storage.save();
                response = "Nice! I've marked this task as done:" + 
                            "\n" + this.taskList.getTask(index).toString();
                }
            else if (input.startsWith("todo ")) {
                String description = input.split(" ", 2)[1];
                taskList.addTask(description);
                    storage.save();
                response = "Got it. I've added this task:\n" + taskList.getLastTask() + 
                           "\nNow you have " + taskList.getTaskLength() + " tasks in the list.";
            }
            
            else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split(" /by ", 2);
                LocalDate myDate = LocalDate.parse(parts[1].trim(), formatter);
                taskList.addTask(parts[0].trim(), myDate);
                storage.save();
                response = "Got it. I've added this task:\n" + taskList.getLastTask() + 
                           "\nNow you have " + taskList.getTaskLength() + " tasks in the list.";
            }
            
            else if (input.equalsIgnoreCase("bye")) {
                response = "Bye. Hope to see you again soon!";
            } 

            else if (input.startsWith("delete ")) {
                String num = input.substring(7).trim();
                int index = Integer.parseInt(num) - 1;

                if (index < 0 || index >= taskList.getTaskLength()) {
                    throw new IndexOutOfBoundsException();
                }
                String taskDetails = this.taskList.getTask(index).toString();
        
                taskList.removeTask(index);
                storage.save();
        
                response = "Noted. I've removed this task:\n" + taskDetails + "\n";
                response += "Now you have " + this.taskList.getTaskLength() + " tasks in the list.";
            }
            else if (input.startsWith("event ")) {
                String descriptionAndDate = input.substring(6);
                String[] parts = descriptionAndDate.split(" /from", 2);
                String description = parts[0].trim();
                String time = parts[1].trim();
                String[] parts2 = time.split(" /to");
                String from = parts2[0].trim();
                String to = parts2[1].trim();
                LocalDate fromDate = LocalDate.parse(from, formatter);
                LocalDate toDate = LocalDate.parse(to, formatter);
                taskList.addTask(description, fromDate, toDate);
                storage.save();
                response = "Got it. I've added this task: \n" + taskList.getLastTask() + "\n" + "Now you have "
                        + taskList.getTaskLength() + " tasks in the list.\n";
            }
            else if (input.startsWith("find ")) {
                // create a temp tasklist to store task that user is finding
                ArrayList<Task> tempArrayList = new ArrayList<>();
                String findDescription = input.substring(5);
                findDescription = findDescription.toLowerCase();
                for (int i = 0; i < taskList.getTaskLength(); i++) {
                    String taskDescription = taskList.getTask(i).getDescription();
                    // convert to lower case so that it is not case sensitive
                    taskDescription = taskDescription.toLowerCase();
                    if (taskDescription.contains(findDescription)) {
                        tempArrayList.add(taskList.getTask(i));
                    }
                }

                if (tempArrayList.size() == 0) {
                    response = "No matching task in your list";
                } else {
                    TaskList tempTaskList = new TaskList(tempArrayList);
                    response = "Here are the matching tasks in your list:\n" + tempTaskList.toString();
                }
            }
            else {
                response = "I'm sorry, but I don't know what that means :-(";
            }
    
        } catch (Exception e) {
            response = "Error: " + e.getMessage();
        }
        
        return response;
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
        return this.NAME;
    }

    /**
     * Returns the greeting text of the chatbot
     *
     * @return A string representing the chatbot's greeting
     */
    public void greet() {
        ui.showLine();
        System.out.println("Hello! I'm " + this.getName());
        System.out.println("What can I do for you?");
        ui.showLine();
    }

    /**
     * Returns the exit text of the chatbot
     *
     * @return A string representing the chatbot's farewell text.
     */
    public void exit() {
        System.out.println("Bye. Hope to see you again soon!");
        ui.showLine();
    }

    /**
     * Starts the main execution loop of the chatbot.
     * * Processes user commands, updates the task list, and synchronises
     * changes to the local storage file until the exit command is received.
     */
    public void run() {
        this.greet();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        input = ui.readCommand();

        while (!input.equalsIgnoreCase("bye")) {
            if (input.equalsIgnoreCase("list")) {
                ui.showMessage("Here are the tasks in your list:");
                ui.showMessage(this.taskList.toString());
                input = ui.readCommand();
            } else if (input.equalsIgnoreCase("bye")) {
                exit();
                break;
            } else if (input.startsWith("mark ")) {
                try {
                    String num = input.substring(5).trim();
                    int index = Integer.parseInt(num) - 1;

                    if (index < 0 || index >= taskList.getTaskLength()) {
                        throw new IndexOutOfBoundsException();
                    }
                    taskList.markTask(index);
                    ui.showLine();
                    ui.showMessage("Nice! I've marked this task as done:");
                    ui.showMessage((this.taskList.getTask(index)).toString());
                    ui.showLine();
                    storage.save();
                    input = ui.readCommand();
                } catch (NumberFormatException e) {
                    System.out.println("Write in this format: \"mark [number]\"");
                    input = ui.readCommand();
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("Number keyed in is out of bound. You have " + taskList.getTaskLength() + " task(s)");
                    input = ui.readCommand();
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    String num = input.substring(7).trim();
                    int index = Integer.parseInt(num) - 1;

                    if (index < 0 || index >= taskList.getTaskLength()) {
                        throw new IndexOutOfBoundsException();
                    }
                    taskList.unmarkTask(index);
                    ui.showLine();
                    ui.showMessage("OK, I've marked this task as not done yet:");
                    ui.showMessage((this.taskList.getTask(index)).toString());
                    ui.showLine();
                    storage.save();
                    input = ui.readCommand();
                } catch (NumberFormatException e) {
                    ui.showError("Write in this format: \"unmark [number]\"");
                    input = ui.readCommand();
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("Number keyed in is out of bound. You have " + taskList.getTaskLength() + " task(s)");
                    input = ui.readCommand();
                }
            } else if (input.startsWith("delete ")) {
                try {
                    String num = input.substring(7).trim();
                    int index = Integer.parseInt(num) - 1;

                    if (index < 0 || index >= taskList.getTaskLength()) {
                        throw new IndexOutOfBoundsException();
                    }
                    ui.showLine();
                    ui.showMessage("Noted. I've removed this task:");
                    ui.showMessage((this.taskList.getTask(index)).toString());
                    taskList.removeTask(index);
                    ui.showMessage("Now you have " + this.taskList.getTaskLength() + " tasks in the list.");
                    ui.showLine();
                    storage.save();
                    input = ui.readCommand();
                } catch (NumberFormatException e) {
                    ui.showError("Write in this format: \"delete [number]\"");
                    input = ui.readCommand();
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("Number keyed in is out of bound. You have " + taskList.getTaskLength() + " task(s)");
                    input = ui.readCommand();
                }
            } else if (input.startsWith("todo ")) {
                try {
                    String[] parts = input.split(" ", 2);
                    String description = parts[1];
                    taskList.addTask(description);
                    storage.save();
                } catch (ArrayIndexOutOfBoundsException e) {
                    ui.showError("Write in this format: \"todo [task description]\"");
                    input = ui.readCommand();
                }
                ui.showError("Got it. I've added this task: \n" + taskList.getLastTask() + "\n"
                        + "Now you have " + taskList.getTaskLength() + " tasks in the list.\n");
                input = ui.readCommand();
            }

            else if (input.startsWith("deadline ")) {
                try {
                    String descriptionAndDate = input.substring(9);
                    String[] parts = descriptionAndDate.split(" /by", 2);
                    String description = parts[0].trim();
                    String date = parts[1].trim();
                    System.out.println(date);
                    LocalDate myDate = LocalDate.parse(date, formatter);
                    taskList.addTask(description, myDate);
                    storage.save();
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("Write in this format: \"deadline [task description] /by [time description]\"");
                    input = ui.readCommand();
                } catch (DateTimeParseException e) {
                    ui.showError("Write in this format: \"deadline [task description] /by yyyy-MM-dd\"");
                    input = ui.readCommand();
                }
                ui.showMessage("Got it. I've added this task: \n" + taskList.getLastTask()
                        + "\n" + "Now you have " + taskList.getTaskLength() + " tasks in the list.\n");
                input = ui.readCommand();
            }

            else if (input.startsWith("event ")) {
                try {
                    String descriptionAndDate = input.substring(6);
                    String[] parts = descriptionAndDate.split(" /from", 2);
                    String description = parts[0].trim();
                    String time = parts[1].trim();
                    String[] parts2 = time.split(" /to");
                    String from = parts2[0].trim();
                    String to = parts2[1].trim();
                    LocalDate fromDate = LocalDate.parse(from, formatter);
                    LocalDate toDate = LocalDate.parse(to, formatter);
                    taskList.addTask(description, fromDate, toDate);
                    storage.save();
                } catch (IndexOutOfBoundsException e) {
                    ui.showMessage(
                            "Write in this format: \"event [task description] /from [time description] /to [time description]\"");
                    input = ui.readCommand();
                } catch (DateTimeParseException e) {
                    ui.showMessage(
                            "Write in this format: \"event [task description] /from yyyy-MM-dd /to yyyy-MM-dd\"");
                    input = ui.readCommand();
                }
                ui.showMessage("Got it. I've added this task: \n" + taskList.getLastTask() + "\n" + "Now you have "
                        + taskList.getTaskLength() + " tasks in the list.\n");
                input = ui.readCommand();
            } else if (input.startsWith("find ")) {
                // create a temp tasklist to store task that user is finding
                ArrayList<Task> tempArrayList = new ArrayList<>();
                String findDescription = input.substring(5);
                findDescription = findDescription.toLowerCase();
                for (int i = 0; i < taskList.getTaskLength(); i++) {
                    String taskDescription = taskList.getTask(i).getDescription();
                    // convert to lower case so that it is not case sensitive
                    taskDescription = taskDescription.toLowerCase();
                    if (taskDescription.contains(findDescription)) {
                        tempArrayList.add(taskList.getTask(i));
                    }
                }

                if (tempArrayList.size() == 0) {
                    ui.showMessage("No matching task in your list");
                } else {
                    TaskList tempTaskList = new TaskList(tempArrayList);
                    ui.showMessage("Here are the matching tasks in your list:");
                    ui.showMessage(tempTaskList.toString());
                    input = ui.readCommand();
                }
            } else {
                try {
                    throw new MaxException("I'm sorry, but I don't know what that means :-(");
                } catch (MaxException e) {
                    ui.showMessage("invalid input, try adding task in this format: \n");
                    ui.showMessage("1. taskList [description]\n");
                    ui.showMessage("2. deadline [description] /by yyyy-MM-dd \n");
                    ui.showMessage("3. event [description] /from yyyy-MM-dd /to yyyy-MM-dd \n");
                } finally {
                    input = ui.readCommand();
                }
            }
        }
        this.exit();
    }

    public static void main(String[] args) {
        new Max("max/data/Max.txt").run();

    }
}
