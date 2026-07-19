package bg.softuni.warranty_tracker.customExceptions.claim;

import org.springframework.http.HttpStatus;

import bg.softuni.warranty_tracker.customExceptions.BaseApplicationException;

public class InvalidStatusTransitionException extends BaseApplicationException {
    public InvalidStatusTransitionException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
