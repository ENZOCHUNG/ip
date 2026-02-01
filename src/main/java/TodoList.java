import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TodoList {
    private List<Task> tasks;

    public TodoList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(String description) {
        tasks.add(new ToDos(description));
    }

    public void addTask(String description, LocalDate by) {
        tasks.add(new Deadlines(description, by));
    }

    public void addTask(String description, LocalDate from, LocalDate to) {
        tasks.add(new Events(description, from, to));
    }

    public int getTaskLength(){
        return tasks.size();
    }

    public Task getTask(int idx) {
        return tasks.get(idx);
    }

    public Task getLastTask() {
        return tasks.get(this.getTaskLength() - 1);
    }

    public void removeTask(int idx) {
        this.tasks.remove(idx);
    }

    public void markTask(int idx) {
        tasks.get(idx).setDone();            
    }

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
