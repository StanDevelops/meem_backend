package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//done
public class InvalidUrlException extends ResponseStatusException {
    public InvalidUrlException() {
        super(HttpStatus.NOT_FOUND, "INVALID_URL");
    }

    public InvalidUrlException(String errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}
