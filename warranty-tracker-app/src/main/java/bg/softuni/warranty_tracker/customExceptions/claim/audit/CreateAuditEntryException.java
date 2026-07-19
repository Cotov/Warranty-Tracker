package bg.softuni.warranty_tracker.customExceptions.claim.audit;

import org.springframework.http.HttpStatus;

import bg.softuni.warranty_tracker.customExceptions.BaseApplicationException;

public class CreateAuditEntryException extends BaseApplicationException {

    public CreateAuditEntryException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
