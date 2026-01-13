import java.time.*;
import java.util.*;

public class EventScheduler {

    private TreeSet<Event> eventsByTime = new TreeSet<>();
    private TreeMap<LocalDate, List<Event>> eventsByDate = new TreeMap<>();

    public boolean addEvent(Event event) {
        if (!isSlotAvailable(event.getStartTime(), event.getEndTime())) {
            return false;
        }
        eventsByTime.add(event);
        eventsByDate
            .computeIfAbsent(event.getStartTime().toLocalDate(), d -> new ArrayList<>())
            .add(event);
        return true;
    }

    public boolean removeEvent(String eventId) {
        Event target = eventsByTime.stream()
                .filter(e -> e.getId().equals(eventId))
                .findFirst().orElse(null);
        if (target == null) return false;

        eventsByTime.remove(target);
        eventsByDate.get(target.getStartTime().toLocalDate()).remove(target);
        return true;
    }

    public List<Event> getEventsOnDate(LocalDate date) {
        return eventsByDate.getOrDefault(date, List.of());
    }

    public List<Event> getEventsInRange(LocalDateTime start, LocalDateTime end) {
        return eventsByTime.subSet(
                new Event("", "", start, start, 1),
                new Event("", "", end, end, 5)
        ).stream().toList();
    }

    public List<Event> getUpcomingEvents(int count) {
        return eventsByTime.tailSet(
                new Event("", "", LocalDateTime.now(), LocalDateTime.now(), 1)
        ).stream().limit(count).toList();
    }

    public Optional<Event> getCurrentEvent() {
        LocalDateTime now = LocalDateTime.now();
        return eventsByTime.stream()
                .filter(e -> !now.isBefore(e.getStartTime())
                          && !now.isAfter(e.getEndTime()))
                .findFirst();
    }

    public List<TimeSlot> findFreeSlots(LocalDate date,
                                        LocalTime dayStart,
                                        LocalTime dayEnd) {
        List<Event> events = getEventsOnDate(date);
        List<TimeSlot> slots = new ArrayList<>();

        LocalDateTime start = LocalDateTime.of(date, dayStart);

        for (Event e : events) {
            if (start.isBefore(e.getStartTime())) {
                slots.add(new TimeSlot(start, e.getStartTime()));
            }
            start = e.getEndTime();
        }

        LocalDateTime dayEndTime = LocalDateTime.of(date, dayEnd);
        if (start.isBefore(dayEndTime)) {
            slots.add(new TimeSlot(start, dayEndTime));
        }
        return slots;
    }

    public List<Event> getEventsBefore(Event event) {
        return new ArrayList<>(eventsByTime.headSet(event));
    }

    public List<Event> getEventsAfter(Event event) {
        return new ArrayList<>(eventsByTime.tailSet(event, false));
    }

    public boolean isSlotAvailable(LocalDateTime start, LocalDateTime end) {
        return eventsByTime.stream().noneMatch(e ->
            start.isBefore(e.getEndTime()) &&
            end.isAfter(e.getStartTime())
        );
    }
}
