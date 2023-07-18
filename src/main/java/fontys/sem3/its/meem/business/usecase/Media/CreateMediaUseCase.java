package fontys.sem3.its.meem.business.usecase.Media;

import fontys.sem3.its.meem.domain.model.MediaTypeEnum;
import fontys.sem3.its.meem.persistence.entity.MediaEntity;

public interface CreateMediaUseCase {
    MediaEntity createMedia(MediaTypeEnum mediaType, String mediaName, String mediaAddress, int mediaSize, Boolean external);
}
