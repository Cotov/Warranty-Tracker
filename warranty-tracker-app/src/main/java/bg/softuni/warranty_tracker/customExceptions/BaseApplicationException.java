package bg.softuni.warranty_tracker.customExceptions;

import org.springframework.http.HttpStatus;

public abstract class BaseApplicationException extends RuntimeException {

    protected HttpStatus statusCode;

    public BaseApplicationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
