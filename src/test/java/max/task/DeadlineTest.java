package max.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class DeadlineTest {
    @Test
    public void deadlineToStringTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate myDate = LocalDate.parse("2019-10-15", formatter);
        Deadlines deadline = new Deadlines("2103T quiz", myDate);
        assertEquals(deadline.toString(), "[D][ ] 2103T quiz (by: Oct 15 2019)");
    }
}