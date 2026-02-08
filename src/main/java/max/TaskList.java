package max;

import max.task.Task;
import max.task.ToDo;
import max.task.Event;
import max.task.Deadline;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
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
    public int getTaskLength(){
        return tasks.size();
    }
    /**
     * Returns the task via indexing
     * @return Task
     */
    public Task getTask(int idx) {
        return tasks.get(idx);
    }
    /**
     * Returns the last task
     * @return Task
     */
    public Task getLastTask() {
        return tasks.get(this.getTaskLength() - 1);
    }
    /**
     * Remove task via indexing
     */
    public void removeTask(int idx) {
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
        tasks.get(idx).setUndone();
    }

    @Override
    public String toString() {
        if (tasks.isEmpty()) {
            return "The list is empty.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i+1)
              .append(".")
              .append(tasks.get(i));
            
            if (i < tasks.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
