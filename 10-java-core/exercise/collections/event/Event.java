import java.time.LocalDateTime;
import java.util.Objects;

public class Event implements Comparable<Event> {

    private String id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int priority;

    public Event(String id, String title,
                 LocalDateTime startTime,
                 LocalDateTime endTime,
                 int priority) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
    }

    public String getId() { return id; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }

    @Override
    public int compareTo(Event o) {
        int cmp = this.startTime.compareTo(o.startTime);
        if (cmp == 0) {
            return Integer.compare(this.priority, o.priority);
        }
        return cmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        return Objects.equals(id, ((Event) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
