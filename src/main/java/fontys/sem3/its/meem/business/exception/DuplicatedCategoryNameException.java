package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicatedCategoryNameException extends ResponseStatusException {
    public DuplicatedCategoryNameException() {
        super(HttpStatus.BAD_REQUEST, "DUPLICATED_CATEGORY_NAME");
    }
}
