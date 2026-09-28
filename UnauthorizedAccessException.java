// EXCEPTION HANDLING
// Thrown when a user attempts to access a function that their role is not authorized to use.

public class UnauthorizedAccessException extends Exception {

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}