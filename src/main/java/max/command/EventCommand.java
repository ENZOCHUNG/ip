package max.command;

import java.time.LocalDate;

import max.Storage;
import max.TaskList;

/**
 * Updates the storage and tasklist when user keys in event
 */
public class EventCommand implements Command {
    private String description;
    private LocalDate fromDate;
    private LocalDate toDate;

    /**
     * Creates an EventCommand with the given description and date range.
     *
     * @param description Description of the event.
     * @param fromDate Start date of the event.
     * @param toDate End date of the event.
     */
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

    public String getDescription() {
        return description;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
