package max.task;

import java.time.LocalDate;

/**
 * Represents a generic task with a description and completion status.
 */
public class Task {
    private boolean isDone;
    private String description;

    /**
     * Creates a task with the given description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    /**
     * Returns the date used for chronological sorting.
     * Returns null if the task does not have a date.
     */
    public LocalDate getSortDate() {
        return null;
    }

    public boolean getTaskStatus() {
        return this.isDone;
    }

    public void setDone() {
        this.isDone = true;
    }

    public void setUndone() {
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return "[" + (this.isDone ? "X" : " ") + "] " + this.description;
    }
}
