package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.DuplicatedSortingGroupNameException;
import fontys.sem3.its.meem.business.exception.EmptyRepositoryException;
import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.business.service.converter.SortingGroupConverter;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.SortingGroup.*;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.SortingGroup;
import fontys.sem3.its.meem.domain.request.SortingGroup.CreateSortingGroupRequest;
import fontys.sem3.its.meem.domain.request.SortingGroup.UpdateSortingGroupNameRequest;
import fontys.sem3.its.meem.domain.response.SortingGroup.GetSortingGroupResponse;
import fontys.sem3.its.meem.domain.response.SortingGroup.GetSortingGroupsResponse;
import fontys.sem3.its.meem.persistence.entity.SortingGroupEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.repository.SortingGroupRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SortingGroupServiceImpl implements
        GetSortingGroupsUseCase,
        GetSortingGroupUseCase,
        CreateSortingGroupUseCase,
        UpdateSortingGroupNameUseCase,
        DeleteSortingGroupUseCase {
    private final SortingGroupRepository sortingGroupRepository;
    private final SortingGroupValidatorUseCase sortingGroupValidator;
    private final AccessTokenAuthenticityUseCase accessTokenAuthenticity;
    private AccessToken requestAccessToken;
    private final UserRepository userRepository;

    @Override
    public GetSortingGroupsResponse getSortingGroups() {

        if (sortingGroupRepository.findByGroupNameNotNullOrderByGroupIdAsc().isEmpty()) {
            throw new EmptyRepositoryException();
        }

        List<SortingGroup> groups = sortingGroupRepository
                .findByGroupNameNotNullOrderByGroupIdAsc()
                .stream()
                .map(SortingGroupConverter::convert)
                .toList();

        return GetSortingGroupsResponse.builder().groups(groups).build();
    }

    @Transactional
    @Override
    public SortingGroupEntity createSortingGroup(@NotNull @NotBlank CreateSortingGroupRequest request) {

        UserEntity user = userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        accessTokenAuthenticity.checkIfAuthoritiesMatch(user, requestAccessToken);

        if (!Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {
            throw new UnauthorizedDataAccessException("NOT_PERMITTED");
        }

        if (sortingGroupRepository.existsByGroupName(request.getGroupName())) //check if a DB entity already matches the request's parameter
        {
            throw new DuplicatedSortingGroupNameException(); //A MATCH! NOT GOOD! Throw a hissy!
        }

        /* declare and construct temporary entity with supplied name */
        SortingGroupEntity tempGroup = new SortingGroupEntity().builder()
                .groupName(request.getGroupName())
                .groupRank(request.getGroupRank())
                .build();
        /* attempt to save the temporary entity and override it with the one returned by the repository after saving */

        return sortingGroupRepository.save(tempGroup);

    }

    @Transactional
    @Override
    public void deleteSortingGroup(@NotNull int groupId) {

        UserEntity user = userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        accessTokenAuthenticity.checkIfAuthoritiesMatch(user, requestAccessToken);

        if (!Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {

            throw new UnauthorizedDataAccessException("NOT_PERMITTED");

        }

        // validate if given ID belongs to an entity from DB
        // an 'InvalidIdentificationException' is thrown if not
        sortingGroupValidator.validateId(groupId);

        /*
            if the checks above were passed and no exception was thrown, proceed to deletion
         */
        sortingGroupRepository.deleteByGroupId(groupId);
    }

    @Override
    public GetSortingGroupResponse getSortingGroupByName(@NotBlank String groupName) {

        if (!sortingGroupRepository.existsByGroupName(groupName)) //check if a DB entity already matches the request's parameter
        {
            throw new InvalidIdentificationException(); //NO MATCH! NOT GOOD! Throw a hissy!
        }

        SortingGroupEntity returnedGroup = sortingGroupRepository.findByGroupName(groupName);
        /*
            if the checks above were passed and no exception was thrown during retrieval of the entity
            then return a response entity with ok status whose body is the converted entity
         */
        return GetSortingGroupResponse.builder().group(SortingGroupConverter.convert(returnedGroup)).build();
    }

    @Transactional
    @Override
    public void updateSortingGroupName(@NotNull @NotBlank UpdateSortingGroupNameRequest request) {

        accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);

        if (!Objects.equals(requestAccessToken.getAuthority(), "ADMIN")) {
            throw new UnauthorizedDataAccessException("NOT_PERMITTED");
        }

        // validate if given ID belongs to an entity from DB
        //throws an 'InvalidIdentificationException'
        sortingGroupValidator.validateId(request.getGroupId());

        if (sortingGroupRepository.existsByGroupName(request.getNewGroupName())) //check if a DB entity already matches the request's parameter
        {

            throw new DuplicatedSortingGroupNameException(); //A MATCH! NOT GOOD! Throw a hissy!

        } else {
            /*
                if the checks above were passed, proceed to fields update
             */
            sortingGroupRepository.updateGroupName(request.getNewGroupName(), request.getGroupId()); //update field with request's value, returns 0 if no updates
        }
    }


}
