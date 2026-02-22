package max.command;

import max.Storage;
import max.TaskList;
/**
 * Updates the storage and tasklist when user keys in mark
 */
public class MarkCommand implements Command {
    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }
    /**
     * Executes the command to mark an existing task to the task list and saves the change to storage.
     *
     * @param tasks The list of tasks where the new task will be added.
     * @param storage The storage handler used to persist the updated task list to the hard disk.
     * @return A string message showing the newly marked task.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        if (index < 0 || index >= tasks.getTaskLength()) {
            return "That task number is out of range. You have " + tasks.getTaskLength() + " task(s).";
        }
        tasks.markTask(index);
        storage.save();
        return "Nice! I've marked this task as done:\n" + tasks.getTask(index);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
