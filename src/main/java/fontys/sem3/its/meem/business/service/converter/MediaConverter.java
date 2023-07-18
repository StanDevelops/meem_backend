package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.Media;
import fontys.sem3.its.meem.persistence.entity.MediaEntity;

public class MediaConverter {
    public static Media convert(MediaEntity mediaEntity) {
        return Media.builder()
                .mediaId(mediaEntity.getMediaId())
                .mediaName(mediaEntity.getMediaName())
                .mediaAddress(mediaEntity.getMediaAddress())
                .mediaSize(mediaEntity.getMediaSize())
                .deleted(mediaEntity.getDeleted())
                .external(mediaEntity.getExternal())
                .mediaType(mediaEntity.getMediaType())
                .build();
    }

}
