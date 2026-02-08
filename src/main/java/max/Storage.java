package max;

import max.task.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.io.IOException;

/** 
 * Handles the loading and saving of task data to a local file.
 * This class is responsible for ensuring that the task list persists
 * across different session of the chatbot.
 */
public class Storage {
    private String filePath;
    private ArrayList<Task> tasks;
    private TaskList taskList;
    private DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH);

    public Storage (String filePath) {
        this.filePath = filePath;
        tasks = new ArrayList<>();
        taskList = new TaskList(tasks);
    }
    /**
     * Loads tasks from the local storage file. 
     * Creates the necessary directory and file if they do not exist.
     * 
     * @return A TaskList populated with tasks from the file.
     * @exception MaxException If the file format is invalid or cannot be read.
     */
    public TaskList load() throws MaxException {
        File file = new File(this.filePath);
        File folder = file.getParentFile();
        
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }
    
        if (!file.exists()) {
            return new TaskList();
        }
        //
        ArrayList<Task> loadedTasks = new ArrayList<>();
        TaskList tempTaskList = new TaskList(loadedTasks);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isEmpty()) continue;

                if (line.contains("[T]")) {
                    String description = line.substring(line.indexOf("]") + 5).trim();
                    tempTaskList.addTask(description);
                }
                else if (line.contains("[D]")) {
                    String description = line.substring(line.indexOf("]") + 5, line.indexOf("(by:")).trim();
                    String by = line.substring(line.indexOf("(by:") + 5, line.lastIndexOf(")"));
                    LocalDate byDate = LocalDate.parse(by, inputFormatter);
                    tempTaskList.addTask(description, byDate);
                } 
                else if (line.contains("[E]")) {
                    String description = line.substring(line.indexOf("]") + 4, line.indexOf("(from:")).trim();
                    String from = line.substring(line.indexOf("from:") + 6, line.indexOf("to:")).trim();
                    String to = line.substring(line.indexOf("to:") + 4, line.indexOf(")")).trim();
                    LocalDate fromDate = LocalDate.parse(from, inputFormatter);
                    LocalDate toDate = LocalDate.parse(to, inputFormatter);
                    tempTaskList.addTask(description, toDate, fromDate);
                }
                if (line.contains("[X]")) {
                    tempTaskList.markTask(tempTaskList.getTaskLength() - 1);
                }
            }
        } catch (FileNotFoundException e) {
            throw new MaxException("File not found!");
        }
        this.taskList = tempTaskList;
        return tempTaskList;
    }
    /**
     * Saved TaskList to .txt.
     * Creates a folder and file it it does not exist.
     * @return Nothing
     * @exception IOException If the file could not be saved.
     */
    public void save() {
        File file = new File(filePath);
        File folder = file.getParentFile();   
        if (folder != null && folder.isDirectory() && !file.exists()) {
            System.out.println("Folder missing. Creating: " + folder.getName());
            folder.mkdirs();
        }
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println(taskList);
        } catch (IOException e) {
            System.err.println("Could not save file: " + e.getMessage());
        }
    }
}
