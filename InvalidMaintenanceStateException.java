// EXCEPTION HANDLING
// Used when an invalid maintenance status transition is attempted.

public class InvalidMaintenanceStateException extends Exception {

    public InvalidMaintenanceStateException(String message) {
        super(message);
    }
}