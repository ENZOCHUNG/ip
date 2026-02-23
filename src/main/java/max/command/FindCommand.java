package max.command;

import java.util.ArrayList;

import max.Storage;
import max.TaskList;
import max.task.Task;

/**
 * Searches for identical keywords from tasklist when user keys in find
 */
public class FindCommand implements Command {
    private final String str;

    public FindCommand(String str) {
        this.str = str;
    }
    /**
     * Executes the search command to find and display tasks that match the user's query.
     * The search is case-insensitive and returns a formatted list of all matching tasks.
     *
     * @param tasks The task list to be searched through.
     * @param storage The storage handler (not directly used by this command, but required by the interface).
     * @return A formatted string listing the matching tasks, or a message stating no matches were found.
     */
    @Override
    public String execute(TaskList tasks, Storage storage) {
        ArrayList<Task> tempArrayList = new ArrayList<>();
        String s = str.toLowerCase();
        for (int i = 0; i < tasks.getTaskLength(); i++) {
            String taskDescription = tasks.getTask(i).getDescription();
            // convert to lower case so that it is not case sensitive
            taskDescription = taskDescription.toLowerCase();
            if (taskDescription.contains(s)) {
                tempArrayList.add(tasks.getTask(i));
            }
        }

        if (tempArrayList.size() == 0) {
            return "No matching task in your list";
        } else {
            TaskList tempTaskList = new TaskList(tempArrayList);
            return "Here are the matching tasks in your list: \n" + tempTaskList.toString();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

}
