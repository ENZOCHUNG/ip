package max.command;

import max.Storage;
import max.TaskList;

/**
 * Updates the storage and tasklist when user keys in delete
 */
public class DeleteCommand implements Command {
    private int idx;

    public DeleteCommand(int idx) {
        this.idx = idx;
    }

    /**
     * Executes the command to remove a task from the task list and saves the change to storage.
     *
     * @param tasks The list of tasks where the new task will be added.
     * @param storage The storage handler used to persist the updated task list to the hard disk.
     * @return A string message confirming the task has been removed and displaying the current list size.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        String taskDetails = tasks.getTask(idx).toString();
        tasks.removeTask(idx);
        storage.save();
        return "Noted. I've removed this task:\n" + taskDetails + "\n"
                        + "Now you have " + tasks.getTaskLength() + " tasks in the list.";
    }

    public int getIdx() {
        return idx;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
