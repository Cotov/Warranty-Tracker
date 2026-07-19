package bg.softuni.warranty_tracker.customExceptions.claim.audit;

import bg.softuni.warranty_tracker.customExceptions.BaseApplicationException;
import org.springframework.http.HttpStatus;

public class GetAuditException extends BaseApplicationException {

    public GetAuditException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
