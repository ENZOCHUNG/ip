package command;

import max.command.Command;
import max.Storage;
import max.TaskList;

public class FindCommand implements Command {
    private final String str; 

    public FindCommand(String str) {
        this.str = str;
    }

    @Override
    public String execute(TaskList tasks, Storage storage) {

    }
    @Override
    
}
