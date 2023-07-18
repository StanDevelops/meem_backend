package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RedundantChangeException extends ResponseStatusException {
    public RedundantChangeException() {
        super(HttpStatus.BAD_REQUEST, "REQUEST_FOR_CHANGE_IS_REDUNDANT");
    }
}
