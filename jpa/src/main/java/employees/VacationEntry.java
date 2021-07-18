package employees;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class VacationEntry {

    private LocalDate start;
    private int daysTaken;

    public VacationEntry() {
    }

    public VacationEntry(LocalDate start, int daysTaken) {
        this.start = start;
        this.daysTaken = daysTaken;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public int getDaysTaken() {
        return daysTaken;
    }

    public void setDaysTaken(int daysTaken) {
        this.daysTaken = daysTaken;
    }

    @Override
    public String toString() {
        return "VacationEntry{" +
                "start=" + start +
                ", daysTaken=" + daysTaken +
                '}';
    }
}
