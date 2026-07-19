package bg.softuni.warranty_tracker.customExceptions.common;

import org.springframework.http.HttpStatus;

import bg.softuni.warranty_tracker.customExceptions.BaseApplicationException;

public class ObjectNotFoundException extends BaseApplicationException {
    public ObjectNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
