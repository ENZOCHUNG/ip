package max.command;

import max.Storage;
import max.TaskList;

/**
 * Represents a generic command that can be executed on a task list.
 * Each command performs an action and may signal application exit.
 */
public interface Command {
    /**
     * Executes the command.
     *
     * @param tasks The task list to operate on.
     * @param storage The storage handler to persist changes.
     * @return A message to be displayed to the user.
     */
    String execute(TaskList tasks, Storage storage);

    /**
     * Indicates whether this command should terminate the application.
     *
     * @return true if the application should exit, false otherwise.
     */
    boolean isExit();
}
