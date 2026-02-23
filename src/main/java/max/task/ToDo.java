package max.task;

/**
 * Represents a to-do task without a specific date.
 */
public class ToDo extends Task {

    /**
     * Creates a ToDo task with a description.
     *
     * @param description Description of the task.
     */
    public ToDo(String description) {
        super(description);
    }
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
