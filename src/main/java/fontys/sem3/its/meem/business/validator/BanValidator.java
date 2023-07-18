package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.usecase.Ban.BanValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.BanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class BanValidator implements BanValidatorUseCase {

    private final BanRepository banRepository;

    /**
     * @param banId
     * @throws InvalidIdentificationException
     */
    @Override
    public void validateId(@NotNull int banId) throws InvalidIdentificationException {
        if (banId == 0 || !banRepository.existsByBanId(banId)) {
            throw new InvalidIdentificationException();
        }
    }
}
