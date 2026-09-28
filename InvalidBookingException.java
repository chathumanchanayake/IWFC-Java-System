// EXCEPTION HANDLING: Custom Exception
// Used when a booking or scheduling operation violate the system's booking rules.

public class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}