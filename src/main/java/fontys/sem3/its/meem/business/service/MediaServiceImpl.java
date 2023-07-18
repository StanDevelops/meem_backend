package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.service.converter.MediaConverter;
import fontys.sem3.its.meem.business.usecase.Media.CreateMediaUseCase;
import fontys.sem3.its.meem.business.usecase.Media.DeleteMediaUseCase;
import fontys.sem3.its.meem.business.usecase.Media.GetMediaUseCase;
import fontys.sem3.its.meem.business.usecase.Media.MediaValidatorUseCase;
import fontys.sem3.its.meem.domain.model.Media;
import fontys.sem3.its.meem.domain.model.MediaTypeEnum;
import fontys.sem3.its.meem.persistence.entity.MediaEntity;
import fontys.sem3.its.meem.persistence.repository.MediaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MediaServiceImpl implements CreateMediaUseCase, DeleteMediaUseCase, GetMediaUseCase {
    private final MediaRepository mediaRepository;
    private final MediaValidatorUseCase mediaValidator;

    /**
     * @param mediaType
     * @param mediaName
     * @param mediaAddress
     * @param mediaSize
     * @param external
     */
    @Override
    public MediaEntity createMedia(MediaTypeEnum mediaType, String mediaName, String mediaAddress, int mediaSize, Boolean external) {
        return mediaRepository.save(MediaEntity.builder()
                .mediaName(mediaName)
                .mediaAddress(mediaAddress)
                .mediaSize(mediaSize)
                .mediaType(mediaType)
                .external(external)
                .deleted(false)
                .build()
        );
    }

    /**
     * @param mediaId
     */
    @Override
    public void deleteMedia(int mediaId) {
        mediaValidator.validateId(mediaId);
        mediaRepository.deleteById(mediaId);
    }

    /**
     * @param mediaId
     * @return
     */
    @Override
    public Media getMediaById(int mediaId) {
        mediaValidator.validateId(mediaId);
        return MediaConverter.convert(mediaRepository.findMediaEntityByMediaId(mediaId));
    }
}
