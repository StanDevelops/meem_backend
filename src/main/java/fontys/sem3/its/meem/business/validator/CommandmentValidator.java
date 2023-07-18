package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.usecase.Commandment.CommandmentValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.CommandmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CommandmentValidator implements CommandmentValidatorUseCase {
    private final CommandmentRepository commandmentRepository;

    /**
     * @param commandmentNr
     * @throws InvalidIdentificationException
     */
    @Override
    public void validateId(int commandmentNr) throws InvalidIdentificationException {
        if (commandmentNr == 0 || !commandmentRepository.existsByCommandmentNr(commandmentNr)) {
            throw new InvalidIdentificationException();
        }
    }
}
