package fontys.sem3.its.meem.business.service.converter;

import fontys.sem3.its.meem.domain.model.Commandment;
import fontys.sem3.its.meem.persistence.entity.CommandmentEntity;

public class CommandmentConverter {
    public static Commandment convert(CommandmentEntity commandmentEntity) {
        return Commandment.builder()
                .commandmentNr(commandmentEntity.getCommandmentNr())
                .commandmentRule(commandmentEntity.getCommandmentRule())
                .punishmentDuration(commandmentEntity.getPunishmentDuration())
                .build();
    }

    //done
}
