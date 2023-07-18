package fontys.sem3.its.meem.business.validator;

import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.usecase.Media.MediaValidatorUseCase;
import fontys.sem3.its.meem.persistence.repository.MediaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@AllArgsConstructor
public class MediaValidator implements MediaValidatorUseCase {
    private final MediaRepository mediaRepository;

    /**
     * @param mediaId
     * @throws InvalidIdentificationException
     */
    @Override
    public void validateId(@NotNull int mediaId) throws InvalidIdentificationException {
        if (!mediaRepository.existsById(mediaId)) {
            throw new InvalidIdentificationException();
        }
    }

    @Override
    public void validateAddress(@NotBlank String mediaAddress) throws InvalidIdentificationException {
        if (!mediaRepository.existsByMediaAddress(mediaAddress)) {
            throw new InvalidIdentificationException();
        }
    }
}
