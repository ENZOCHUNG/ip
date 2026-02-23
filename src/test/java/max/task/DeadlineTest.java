package max.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    @Test
    public void deadlineToStringTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate myDate = LocalDate.parse("2019-10-15", formatter);
        Deadline deadline = new Deadline("2103T quiz", myDate);
        assertEquals(deadline.toString(), "[D][ ] 2103T quiz (by: Oct 15 2019)");

    }
}
