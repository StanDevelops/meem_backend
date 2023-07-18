package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.User;
import fontys.sem3.its.meem.persistence.entity.UserEntity;

public class UserConverter {
    public static User convert(UserEntity userEntity) {
        return User.builder()
                .userId(userEntity.getUserId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .dateRegistered(userEntity.getDateRegistered())
                .gender(userEntity.getGender())
                .userRating(userEntity.getUserRating())
                .honourableUser(userEntity.getHonourableUser())
                .userRole(userEntity.getUserRole())
                .isBanned(userEntity.getIsBanned())
                .profilePicture(userEntity.getProfilePicture().getMediaAddress())
                .deleted(userEntity.getDeleted())
                .build();
    }
    //done
}
