package fontys.sem3.its.meem.business.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicatedSortingGroupNameException extends ResponseStatusException {
    public DuplicatedSortingGroupNameException() {
        super(HttpStatus.BAD_REQUEST, "DUPLICATED_SORTING_GROUP_NAME");
    }
}
