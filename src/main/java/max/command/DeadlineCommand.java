package max.command;

import java.time.LocalDate;

import max.Storage;
import max.TaskList;
/**
 * Updates the storage and tasklist when user keys in deadline
 */
public class DeadlineCommand implements Command {
    private String description;
    private LocalDate deadline;
    /**
     * Creates a DeadlineCommand with a description and due date.
     *
     * @param description Description of the task.
     * @param deadline Due date of the task.
     */
    public DeadlineCommand(String description, LocalDate deadline) {
        this.description = description;
        this.deadline = deadline;
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
        tasks.addTask(description, deadline);
        storage.save();
        return "Got it. I've added this task:\n" + tasks.getLastTask()
                + "\nNow you have " + tasks.getTaskLength() + " tasks in the list.";
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
