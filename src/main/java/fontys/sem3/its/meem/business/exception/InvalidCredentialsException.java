package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCredentialsException extends ResponseStatusException {
    public InvalidCredentialsException() {
        super(HttpStatus.BAD_REQUEST, "INVALID_CREDENTIALS");
    }

    public InvalidCredentialsException(String errorCode) {
        super(HttpStatus.BAD_REQUEST, errorCode);
    }
}