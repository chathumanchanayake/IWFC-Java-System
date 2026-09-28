// EXCEPTION HANDLING
// Thrown when an attempt is made to add data with an ID that already exists in the system.

public class DuplicateDataException extends Exception {

    public DuplicateDataException(String message) {
        super(message);
    }
}