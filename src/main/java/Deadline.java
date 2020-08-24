import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {

    protected LocalDate dateBy;
    protected LocalTime timeBy;

    public Deadline(String description, String by) throws DukeException {
        super(description);
        try {
            String[] parts = by.split(" ", 2);
            this.dateBy = LocalDate.parse(parts[0]);
            this.timeBy = LocalTime.parse(parts[1]);
        } catch (DateTimeParseException e) {
            throw new DukeException("Please input date and time in correct format: " +
                    "'yyyy-MM-dd HH:MM' (24-hour time format)");
        }
    }

    @Override
    public String toString() {
        String modifier = (this.timeBy.isAfter(LocalTime.NOON)) ? "pm" : "am";
        return "[D]" + super.toString() + " (by: " + this.dateBy.format(DateTimeFormatter.ofPattern("MMM d yyyy")) +
                ", " + this.timeBy.format(DateTimeFormatter.ofPattern("hh:mm")) + modifier + ")";
    }
}