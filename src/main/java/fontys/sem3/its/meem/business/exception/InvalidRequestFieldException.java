package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRequestFieldException extends ResponseStatusException {
    public InvalidRequestFieldException() {
        super(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_FIELD");
    }

    public InvalidRequestFieldException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
