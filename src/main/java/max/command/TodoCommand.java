package max.command;

import max.Storage;
import max.TaskList;
/**
 * Updates the storage and tasklist when user keys in todo
 */
public class TodoCommand implements Command {
    private String description;

    public TodoCommand(String description) {
        this.description = description;
    }
    /**
     * Executes the command to add a new task to the task list and saves the change to the storage.
     *
     * @param tasks The list of tasks where the new task will be added.
     * @param storage The storage handler used to persist the updated task list to the hard disk.
     * @return A string message confirming the task has been added and displaying the current list size.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        tasks.addTask(description);
        storage.save();
        return "Got it. I've added this task:\n" + tasks.getLastTask()
                + "\nNow you have " + tasks.getTaskLength() + " tasks in the list.";
    }

    public String getDescription() {
        return description;
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
