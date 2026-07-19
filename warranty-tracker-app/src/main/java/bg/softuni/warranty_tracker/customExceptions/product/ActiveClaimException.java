package bg.softuni.warranty_tracker.customExceptions.product;

import bg.softuni.warranty_tracker.customExceptions.BaseApplicationException;
import org.springframework.http.HttpStatus;

public class ActiveClaimException extends BaseApplicationException {
    public ActiveClaimException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
