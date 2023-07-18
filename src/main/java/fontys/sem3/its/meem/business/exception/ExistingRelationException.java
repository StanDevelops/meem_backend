package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ExistingRelationException extends ResponseStatusException {
    public ExistingRelationException() {
        super(HttpStatus.BAD_REQUEST, "RELATION_ALREADY_EXISTS");
    }
}
