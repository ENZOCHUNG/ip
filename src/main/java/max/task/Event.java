package max.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * Represents an event task with a start and end date.
 */
public class Event extends Task {
    private LocalDate from;
    private LocalDate to;
    /**
     * Creates an Event task with a description and date range.
     *
     * @param description Description of the event.
     * @param from Start date of the event.
     * @param to End date of the event.
     */
    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + " to: " + this.to.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")";
    }
}
