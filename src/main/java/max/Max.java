package max;

import max.task.Task;
import max.task.ToDos;
import max.task.Events;
import max.task.Deadlines;

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

public class Max {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private String input;
    private final String NAME = "MAX";

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

    public String getName() {
        return this.NAME;
    }

    public void greet() {
        ui.showLine();
        System.out.println("Hello! I'm " + this.getName());
        System.out.println("What can I do for you?");
        ui.showLine();
    }

    public void exit() {
        System.out.println("Bye. Hope to see you again soon!");
        ui.showLine();
    }

    public void run() {
        this.greet();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        input = ui.readCommand();
        
        while(!input.equalsIgnoreCase("bye")) {
            if (input.equalsIgnoreCase("list")) {
                ui.showMessage("Here are the tasks in your list:");
                ui.showMessage(this.taskList.toString());
                input = ui.readCommand();
            }
            else if (input.equalsIgnoreCase("bye")) {
                exit();
                break;
            }
            else if (input.startsWith("mark ")) {
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
                } catch (NumberFormatException e){
                    System.out.println("Write in this format: \"mark [number]\"");
                    input = ui.readCommand();
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("Number keyed in is out of bound. You have " + taskList.getTaskLength() + " task(s)");
                    input = ui.readCommand();
                }
            }
            else if (input.startsWith("unmark ")) {
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
                } catch (NumberFormatException e){
                    ui.showError("Write in this format: \"unmark [number]\"");
                    input = ui.readCommand();
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("Number keyed in is out of bound. You have " + taskList.getTaskLength() + " task(s)");
                    input = ui.readCommand();
                }  
            }
            else if (input.startsWith("delete ")) {
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

                } catch (NumberFormatException e){
                    ui.showError("Write in this format: \"delete [number]\"");
                    input = ui.readCommand();
                } catch (IndexOutOfBoundsException e) {
                    ui.showError("Number keyed in is out of bound. You have " + taskList.getTaskLength() + " task(s)");
                    input = ui.readCommand();
                }
            }
            else if (input.startsWith("todo ")) {
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
                    ui.showMessage("Write in this format: \"event [task description] /from [time description] /to [time description]\"");
                    input = ui.readCommand();
                } catch (DateTimeParseException e) {
                    ui.showMessage("Write in this format: \"event [task description] /from yyyy-MM-dd /to yyyy-MM-dd\"");
                    input = ui.readCommand();
                }
                ui.showMessage("Got it. I've added this task: \n" + taskList.getLastTask() + "\n" + "Now you have " 
                        + taskList.getTaskLength() + " tasks in the list.\n");
                input = ui.readCommand();
            }
            else {
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
