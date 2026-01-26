public class Events extends Task {
    String from;
    String by;
    public Events(String description, String from, String by) {
        super(description);
        this.from = from;
        this.by = by;
    }
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.by + ")";
    }
}
