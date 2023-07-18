package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//done
public class InvalidIdentificationException extends ResponseStatusException {
    public InvalidIdentificationException() {
        super(HttpStatus.NOT_FOUND, "INVALID_IDENTIFIER");
    }

    public InvalidIdentificationException(String errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}
