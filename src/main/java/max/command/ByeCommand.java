package max.command;

import max.Storage;
import max.TaskList;
/**
 * Execute command when user keys in Bye
 */
public class ByeCommand implements Command {
    /**
     * Return a message from Max saying bye
     *
     * @param tasks The list of tasks where the new task will be added.
     * @param storage The storage handler used to persist the updated task list to the hard disk.
     * @return A string message showing max saying bye
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        return "Bye. Hope to see you again soon!";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
