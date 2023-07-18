package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.FrontendUserRelation;
import fontys.sem3.its.meem.persistence.entity.UserRelationEntity;

public class UserRelationConverter {
    /**
     * @param userRelationEntity
     * @return FrontendUserRelation
     * @should convert User Relation Entity to Frontend User Relation object and return it
     */
    public static FrontendUserRelation convert(UserRelationEntity userRelationEntity) {
        return FrontendUserRelation.builder()
                .relationId(userRelationEntity.getRelationId())
                .primaryUserId(userRelationEntity.getPrimaryUser().getUserId())
                .secondaryUserId(userRelationEntity.getSecondaryUser().getUserId())
                .secondaryUserUsername(userRelationEntity.getSecondaryUser().getUsername())
                .relationType(userRelationEntity.getRelationType().toString())
                .secondaryUserPfpUrl(userRelationEntity.getSecondaryUser().getProfilePicture().getMediaAddress())
                .build();
    }
    //done
}
