package max.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TaskTest {
    @Test
    public void testInitialStatus() {
        Task task = new Task("Read book");
        assertFalse(task.getTaskStatus(), "New task should not be done");
    }

    @Test
    public void testToString() {
        Task task = new Task("test");
        assertEquals("[ ] test", task.toString());
    }

    @Test
    public void testSetUndone() {
        Task task = new Task("Go for a run");
        task.setDone();
        assertTrue(task.getTaskStatus());
    }
}
