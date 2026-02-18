package max.command;

import max.Storage;
import max.TaskList;

public interface Command {
    String execute(TaskList tasks, Storage storage);
    boolean isExit();
}
