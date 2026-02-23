package max.command;

import max.Storage;
import max.TaskList;

/**
 * Sorts tasks chronologically.
 */
public class SortCommand implements Command {

    @Override
    public String execute(TaskList tasks, Storage storage) {
        tasks.sortTasksChronologically();
        storage.save();
        return "Tasks have been sorted chronologically.";
    }

    @Override
    public boolean isExit() {
        return false;
    }
}