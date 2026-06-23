package bg.softuni.warranty_tracker.customExceptions;

public class ActiveClaimException extends RuntimeException {
    public ActiveClaimException(String message) {
        super(message);
    }
}
