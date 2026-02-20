package max.command;

import max.Storage;
import max.TaskList;

public class UnmarkCommand implements Command {
    private final int index; 

    public MarkCommand(int index) {
        this.index = index;
    }

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
