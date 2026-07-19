package bg.softuni.warranty_tracker.customExceptions.common;

import org.springframework.http.HttpStatus;

import bg.softuni.warranty_tracker.customExceptions.BaseApplicationException;

public class DuplicateEntityException extends BaseApplicationException {
    public DuplicateEntityException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
