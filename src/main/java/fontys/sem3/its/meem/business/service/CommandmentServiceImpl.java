package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.EmptyRepositoryException;
import fontys.sem3.its.meem.business.exception.InvalidAccessTokenException;
import fontys.sem3.its.meem.business.service.converter.CommandmentConverter;
import fontys.sem3.its.meem.business.usecase.Commandment.CreateCommandmentUseCase;
import fontys.sem3.its.meem.business.usecase.Commandment.GetCommandmentsUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.Commandment;
import fontys.sem3.its.meem.domain.request.Commandment.CreateCommandmentRequest;
import fontys.sem3.its.meem.domain.response.Commandment.GetCommandmentsResponse;
import fontys.sem3.its.meem.persistence.entity.CommandmentEntity;
import fontys.sem3.its.meem.persistence.repository.CommandmentRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CommandmentServiceImpl implements CreateCommandmentUseCase, GetCommandmentsUseCase {
    private AccessToken requestAccessToken;
    private final CommandmentRepository commandmentRepository;
    private final UserRepository userRepository;

    private void checkIfAuthoritiesMatch() {
        //checks if the access token user authority matches the user authority in the database
        if (!Objects.equals(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false).getUserRole().toString(), requestAccessToken.getAuthority())) {
            throw new InvalidAccessTokenException("AUTHORITY_NOT_MATCHING_USER_AUTHORITY");
        }
    }

    /**
     * @param request
     */
    @Override
    public void createCommandment(CreateCommandmentRequest request) {
        checkIfAuthoritiesMatch();
        if (!Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {
            commandmentRepository.save(
                    CommandmentEntity.builder()
                            .commandmentRule(request.getCommandmentRule())
                            .punishmentDuration(request.getPunishmentDuration())
                            .build()
            );
        }
    }

    /**
     * @return
     */
    @Override
    public GetCommandmentsResponse getCommandments() {

        List<CommandmentEntity> returnedCommandments = commandmentRepository.findAll();

        checkIfRepoIsEmpty(returnedCommandments);

        List<Commandment> convertedCommandments = returnedCommandments
                .stream()
                .map(CommandmentConverter::convert)
                .toList();

        return GetCommandmentsResponse.builder().commandments(convertedCommandments).build();
    }

    private void checkIfRepoIsEmpty(List<CommandmentEntity> returnedCommandments) {
        if (returnedCommandments.isEmpty()) {
            throw new EmptyRepositoryException();
        }
    }
}
