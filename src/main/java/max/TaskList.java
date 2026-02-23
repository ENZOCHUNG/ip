package max;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import max.task.Deadline;
import max.task.Event;
import max.task.Task;
import max.task.ToDo;

/**
 * This class stores the different Task as a list
 * Allows for adding, removing and accessing task in the list
 */
public class TaskList {
    private List<Task> tasks;

    public TaskList(ArrayList<Task> arrayList) {
        this.tasks = arrayList;
    }

    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    /**
     * Assert method to check if input causes out of bound error
     */
    private void assertValidIndex(int idx) {
        assert idx >= 0 && idx < tasks.size() : "Invalid task index: " + idx;
    }
    /**
     * Creates a ToDo task and append to TaskList
     * @param description The description of the todo task
     */
    public void addTask(String description) {
        tasks.add(new ToDo(description));
    }
    /**
     * Creates a Deadline task and append to TaskList
     * @param description The description of the deadline task
     * @param by the date of the task's deadline
     */
    public void addTask(String description, LocalDate by) {
        tasks.add(new Deadline(description, by));
    }
    /**
     * Creates a Event task and append to TaskList
     * @param description The description of the event task
     * @param from the date of the event's start date
     * @param to the date of the event's end date
     */
    public void addTask(String description, LocalDate from, LocalDate to) {
        tasks.add(new Event(description, from, to));
    }
    /**
     * Returns the length of the task
     */
    public int getTaskLength() {
        return tasks.size();
    }
    /**
     * Returns the task via indexing
     * @return Task
     */
    public Task getTask(int idx) {
        assertValidIndex(idx);
        return tasks.get(idx);
    }

    /**
     * Returns the last task
     * @return Task
     */
    public Task getLastTask() {
        assert tasks.size() > 0 : "Task List should have at least one task";
        return tasks.get(this.getTaskLength() - 1);
    }

    /**
     * Remove task via indexing
     */
    public void removeTask(int idx) {
        assertValidIndex(idx);
        this.tasks.remove(idx);
    }

    /**
     * Set task as done via indexing
     */
    public void markTask(int idx) {
        tasks.get(idx).setDone();
    }
    /**
     * Set task as undone via indexing
     */
    public void unmarkTask(int idx) {
        assertValidIndex(idx);
        tasks.get(idx).setUndone();
    }
    /**
     * Sorts the tasks in chronological order based on their associated dates.
     *
     * Tasks that contain a date (e.g., Deadline, Event)
     * are sorted in ascending order using their {getSortDate()} value.
     * Tasks without a date (e.g., {ToDo}) are placed at the end of the list.
     *
     * If both tasks being compared do not have dates, their relative
     * order remains unchanged.
     */
    public void sortTasksChronologically() {
        tasks.sort(new Comparator<Task>() {
            @Override
            public int compare(Task t1, Task t2) {
                LocalDate d1 = t1.getSortDate();
                LocalDate d2 = t2.getSortDate();

                if (d1 == null && d2 == null) {
                    return 0;
                } else if (d1 == null) {
                    return 1; // Todos go last
                } else if (d2 == null) {
                    return -1;
                } else {
                    return d1.compareTo(d2);
                }
            }
        });
    }
    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "The list is empty.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1)
                .append(".")
                .append(tasks.get(i));

            if (i < tasks.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
