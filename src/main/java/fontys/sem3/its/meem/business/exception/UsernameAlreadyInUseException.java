package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UsernameAlreadyInUseException extends ResponseStatusException {
    public UsernameAlreadyInUseException() {
        super(HttpStatus.BAD_REQUEST, "USERNAME_ALREADY_IN_USE");
    }
}
