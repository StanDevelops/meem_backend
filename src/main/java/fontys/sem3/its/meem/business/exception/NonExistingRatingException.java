package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NonExistingRatingException extends ResponseStatusException {
    public NonExistingRatingException() {
        super(HttpStatus.NOT_FOUND, "RATING_DOES_NOT_EXIST");
    }
}
