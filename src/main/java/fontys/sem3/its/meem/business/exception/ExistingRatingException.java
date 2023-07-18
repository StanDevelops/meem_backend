package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExistingRatingException extends ResponseStatusException {
    public ExistingRatingException() {
        super(HttpStatus.BAD_REQUEST, "RATING_ALREADY_EXISTS");
    }
}
