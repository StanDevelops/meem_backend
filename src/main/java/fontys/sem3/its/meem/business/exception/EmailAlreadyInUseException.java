package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailAlreadyInUseException extends ResponseStatusException {
    public EmailAlreadyInUseException() {
        super(HttpStatus.BAD_REQUEST, "EMAIL_ALREADY_IN_USE");
    }
}
