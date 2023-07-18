package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ActiveBanException extends ResponseStatusException {
    public ActiveBanException() {
        super(HttpStatus.UNAUTHORIZED, "USER_HAS_AN_ACTIVE_BAN");
    }
}