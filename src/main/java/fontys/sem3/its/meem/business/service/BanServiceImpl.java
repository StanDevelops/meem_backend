package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.ActiveBanException;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.ActivityHistory.CreateActivityHistoryUseCase;
import fontys.sem3.its.meem.business.usecase.Ban.CheckBanExpirationUseCase;
import fontys.sem3.its.meem.business.usecase.Ban.CreateBanUseCase;
import fontys.sem3.its.meem.business.usecase.Commandment.CommandmentValidatorUseCase;
import fontys.sem3.its.meem.business.usecase.User.SetUserAsBannedUseCase;
import fontys.sem3.its.meem.business.usecase.User.SetUserAsUnbannedUseCase;
import fontys.sem3.its.meem.business.usecase.User.UserValidatorUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.ActivityTypeEnum;
import fontys.sem3.its.meem.domain.request.Ban.CreateBanRequest;
import fontys.sem3.its.meem.persistence.entity.BanEntity;
import fontys.sem3.its.meem.persistence.entity.CommandmentEntity;
import fontys.sem3.its.meem.persistence.repository.BanRepository;
import fontys.sem3.its.meem.persistence.repository.CommandmentRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@AllArgsConstructor
public class BanServiceImpl implements CreateBanUseCase, CheckBanExpirationUseCase {
    private final UserValidatorUseCase userValidator;
    private final CommandmentValidatorUseCase commandmentValidator;
    private final CommandmentRepository commandmentRepository;
    private final UserRepository userRepository;
    private final BanRepository banRepository;
    private final CreateActivityHistoryUseCase createActivityHistoryUseCase;
    private final SetUserAsBannedUseCase setUserAsBannedUseCase;
    private final SetUserAsUnbannedUseCase setUserAsUnbannedUseCase;
    private final AccessTokenAuthenticityUseCase accessTokenAuthenticityUseCase;
    private AccessToken requestAccessToken;

    /**
     * @param request
     */
    @Override
    public void createBan(@NotNull CreateBanRequest request) {

        userValidator.validateId(request.getUserId());

        userValidator.validateId(request.getModId());

        accessTokenAuthenticityUseCase.checkIfUserIdMatches(requestAccessToken, userRepository.findByUserIdAndDeleted(request.getModId(), false));

        if (banRepository.getNonExpiredBan(request.getUserId()) != null) {
            throw new ActiveBanException();
        }

        commandmentValidator.validateId(request.getCommandmentNr());
        Timestamp timestamp = Timestamp.from(Instant.now());
        CommandmentEntity commandment = commandmentRepository.findByCommandmentNr(request.getCommandmentNr());
        banRepository.save(
                BanEntity.builder()
                        .userId(userRepository.findByUserIdAndDeleted(request.getUserId(), false))
                        .modId(userRepository.findByUserIdAndDeleted(request.getModId(), false))
                        .dateBanned(timestamp)
                        .commandmentNr(commandment)
                        .expirationDate(Date.valueOf(String.valueOf(timestamp.toLocalDateTime().with(Time.valueOf(commandment.getPunishmentDuration()).toLocalTime()))))
                        .build()
        );

        setUserAsBannedUseCase.makeUserBanned(request.getUserId());

        createActivityHistoryUseCase.createActivityHistoryForUserInteractionsWithAnotherUser(
                userRepository.findByUserIdAndDeleted(request.getModId(), false),
                ActivityTypeEnum.BANNED,
                userRepository.findByUserIdAndDeleted(request.getUserId(), false)
        );

    }

    /**
     * @param userId
     */
    @Override
    public void checkBanExpiration(int userId) {

        userValidator.validateId(userId);

        BanEntity returnedBan = banRepository.getNonExpiredBan(userId);

        if (banRepository.checkIfBanHasExpired(returnedBan.getBanId())) {

            banRepository.setBanAsExpired(returnedBan.getBanId());

            setUserAsUnbannedUseCase.makeUserUnbanned(userId);
        }
    }

}
