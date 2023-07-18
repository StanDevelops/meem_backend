package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmptyRepositoryException extends ResponseStatusException {
    public EmptyRepositoryException() {
        super(HttpStatus.NOT_FOUND, "NO_RECORDS_FOUND");
    }

}
