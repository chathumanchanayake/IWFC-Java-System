// DESIGN PATTERN: Observer Pattern (Behavioural)
// Defines the notification behaviour required by objects that want to receive system updates.

public interface NotificationObserver {

    void update(String message);
}