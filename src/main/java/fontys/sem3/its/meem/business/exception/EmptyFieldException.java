package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmptyFieldException extends ResponseStatusException {
    public EmptyFieldException() {
        super(HttpStatus.BAD_REQUEST, "EMPTY_FIELD");
    }

    public EmptyFieldException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}
