package max.command;

import max.Storage;
import max.TaskList;
/**
 * Displays the tasklist when user keys in list
 */
public class ListCommand implements Command {
    @Override
    public String execute(TaskList tasks, Storage storage) {
        return "Here are the tasks in your list:\n" + tasks;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}