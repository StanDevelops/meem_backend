package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnauthorizedDataAccessException extends ResponseStatusException {
    public UnauthorizedDataAccessException(String errorCause) {
        super(HttpStatus.FORBIDDEN, errorCause);
    }

    public UnauthorizedDataAccessException() {
        super(HttpStatus.FORBIDDEN, "INSUFFICIENT_AUTHORITY");
    }
}