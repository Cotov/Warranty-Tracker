package bg.softuni.warranty_tracker.customExceptions.user;

import org.springframework.http.HttpStatus;

import bg.softuni.warranty_tracker.customExceptions.BaseApplicationException;

public class UserException extends BaseApplicationException {
    public UserException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
