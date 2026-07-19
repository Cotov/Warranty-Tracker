package bg.softuni.warranty_tracker.customExceptions.user;

import org.springframework.http.HttpStatus;

import bg.softuni.warranty_tracker.customExceptions.BaseApplicationException;

public class UserForbiddenException extends BaseApplicationException {
    public UserForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
