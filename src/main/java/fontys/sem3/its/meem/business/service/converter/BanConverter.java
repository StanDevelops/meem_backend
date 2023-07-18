package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.Ban;
import fontys.sem3.its.meem.persistence.entity.BanEntity;

public class BanConverter {
    public static Ban convert(BanEntity banEntity) {
        return Ban.builder()
                .banId(banEntity.getBanId())
                .userId(banEntity.getUserId().getUserId())
                .modId(banEntity.getModId().getUserId())
                .commandmentNr(banEntity.getBanId())
                .dateBanned(banEntity.getDateBanned())
                .expirationDate(banEntity.getExpirationDate())
                .expired(banEntity.getExpired())
                .build();
    }

    //done
}
