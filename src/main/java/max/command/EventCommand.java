package max.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import max.Storage;
import max.TaskList;
/**
 * Updates the storage and tasklist when user keys in event
 */
public class EventCommand implements Command {
    String description;
    LocalDate fromDate;
    LocalDate toDate;

    public EventCommand(String description, LocalDate fromDate, LocalDate toDate) {
        this.description = description;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    /**
     * Executes the command to add a new task to the task list and saves the change to storage.
     *
     * @param tasks The list of tasks where the new task will be added.
     * @param storage The storage handler used to persist the updated task list to the hard disk.
     * @return A string message confirming the task has been added and displaying the current list size.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        tasks.addTask(description, fromDate, toDate);
        storage.save();
        return "Got it. I've added this task: \n" + tasks.getLastTask() + "\n" + "Now you have "
                        + tasks.getTaskLength() + " tasks in the list.\n";
    }

    @Override
    public boolean isExit() {
        return false;
    }
}