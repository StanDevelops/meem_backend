package fontys.sem3.its.meem.business.service;

import fontys.sem3.its.meem.business.exception.DuplicatedSortingGroupNameException;
import fontys.sem3.its.meem.business.exception.EmptyRepositoryException;
import fontys.sem3.its.meem.business.exception.InvalidIdentificationException;
import fontys.sem3.its.meem.business.exception.UnauthorizedDataAccessException;
import fontys.sem3.its.meem.business.usecase.AccessToken.AccessTokenAuthenticityUseCase;
import fontys.sem3.its.meem.business.usecase.SortingGroup.SortingGroupValidatorUseCase;
import fontys.sem3.its.meem.domain.model.AccessToken;
import fontys.sem3.its.meem.domain.model.SortingGroup;
import fontys.sem3.its.meem.domain.model.UserRoleEnum;
import fontys.sem3.its.meem.domain.request.SortingGroup.CreateSortingGroupRequest;
import fontys.sem3.its.meem.domain.request.SortingGroup.UpdateSortingGroupNameRequest;
import fontys.sem3.its.meem.domain.response.SortingGroup.GetSortingGroupResponse;
import fontys.sem3.its.meem.domain.response.SortingGroup.GetSortingGroupsResponse;
import fontys.sem3.its.meem.persistence.entity.SortingGroupEntity;
import fontys.sem3.its.meem.persistence.entity.UserEntity;
import fontys.sem3.its.meem.persistence.repository.SortingGroupRepository;
import fontys.sem3.its.meem.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SortingGroupServiceImplTest {
    @Mock
    private SortingGroupRepository sortingGroupRepository;
    @Mock
    private SortingGroupValidatorUseCase sortingGroupValidator;
    @Mock
    private AccessTokenAuthenticityUseCase accessTokenAuthenticity;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccessToken requestAccessToken;
    @InjectMocks
    private SortingGroupServiceImpl sortingGroupService;

    @Test
    void getSortingGroups_shouldReturnAllSortingGroupsConvertedFromEntityToRegular_whenNotEmpty() {
        SortingGroupEntity top = SortingGroupEntity.builder()
                .groupId(1)
                .groupName("Top")
                .groupRank(1)
                .build();

        SortingGroupEntity hot = SortingGroupEntity.builder()
                .groupId(2)
                .groupName("Hot")
                .groupRank(2)
                .build();


        when(sortingGroupRepository.findByGroupNameNotNullOrderByGroupIdAsc()).thenReturn(List.of(
                top,
                hot
        ));

        GetSortingGroupsResponse actualResponse = sortingGroupService.getSortingGroups();

        GetSortingGroupsResponse expectedResponse = GetSortingGroupsResponse.builder().groups(List.of(
                        SortingGroup.builder().groupId(1).groupRank(1).groupName("Top").build(),
                        SortingGroup.builder().groupId(2).groupRank(2).groupName("Hot").build()))
                .build();

        assertEquals(expectedResponse, actualResponse);

        verify(sortingGroupRepository, times(2)).findByGroupNameNotNullOrderByGroupIdAsc();

    }

    @Test
    void getSortingGroups_shouldThrowEmptyRepositoryException_whenEmpty() {

        List<SortingGroupEntity> emptyList = new ArrayList<>();

        when(sortingGroupRepository.findByGroupNameNotNullOrderByGroupIdAsc()).thenReturn(emptyList);

        EmptyRepositoryException exception = assertThrows(EmptyRepositoryException.class, () -> sortingGroupService.getSortingGroups());

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        assertEquals("NO_RECORDS_FOUND", exception.getReason());

        verify(sortingGroupRepository).findByGroupNameNotNullOrderByGroupIdAsc();

    }

    //
    @Test
    void createSortingGroup_ShouldSaveTheSortingGroup_WhenNameIsNonDuplicateNonNullAndAdminAuthority() {

        SortingGroupEntity testSortingGroup = SortingGroupEntity.builder().groupRank(1).groupName("Test").build();

        SortingGroupEntity expectedSavedSortingGroup = SortingGroupEntity.builder().groupId(1).groupRank(1).groupName("Test").build();

        CreateSortingGroupRequest request = CreateSortingGroupRequest.builder().groupRank(1).groupName("Test").build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.ADMIN)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(
                userRepository.findByUserIdAndDeleted(
                        requestAccessToken.getUserId(), false),
                requestAccessToken)
        ).thenReturn(true);

        when(sortingGroupRepository.existsByGroupName(request.getGroupName())).thenReturn(false);

        when(sortingGroupRepository.save(testSortingGroup)).thenReturn(expectedSavedSortingGroup);

        SortingGroupEntity actuallySavedSortingGroup = sortingGroupService.createSortingGroup(request);

        assertEquals(expectedSavedSortingGroup, actuallySavedSortingGroup);

        verify(requestAccessToken).getAuthority();

        verify(sortingGroupRepository).existsByGroupName(request.getGroupName());

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity)
                .checkIfAuthoritiesMatch(
                        userRepository.findByUserIdAndDeleted(
                                requestAccessToken.getUserId(), false), requestAccessToken);

        verify(sortingGroupRepository).save(testSortingGroup);

    }

    @Test
    void createSortingGroup_ShouldThrowDuplicatedSortingGroupNameException_WhenNameIsDuplicateAndAdminAuthority() {

        CreateSortingGroupRequest request = CreateSortingGroupRequest.builder().groupRank(1).groupName("Test").build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.ADMIN)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(
                userRepository.findByUserIdAndDeleted(
                        requestAccessToken.getUserId(), false),
                requestAccessToken)
        ).thenReturn(true);

        when(sortingGroupRepository.existsByGroupName(request.getGroupName())).thenReturn(true);

        DuplicatedSortingGroupNameException exception = assertThrows(DuplicatedSortingGroupNameException.class, () -> sortingGroupService.createSortingGroup(request));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        assertEquals("DUPLICATED_SORTING_GROUP_NAME", exception.getReason());

        verify(requestAccessToken).getAuthority();

        verify(sortingGroupRepository).existsByGroupName(request.getGroupName());

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity)
                .checkIfAuthoritiesMatch(
                        userRepository.findByUserIdAndDeleted(
                                requestAccessToken.getUserId(), false), requestAccessToken);
    }

    @Test
    void createSortingGroup_ShouldThrowUnauthorizedDataAccessException_WhenNotAdminAuthority() {

        CreateSortingGroupRequest request = CreateSortingGroupRequest.builder().groupName("Test").groupRank(1).build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.REGULAR)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("REGULAR");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(
                userRepository.findByUserIdAndDeleted(
                        requestAccessToken.getUserId(), false),
                requestAccessToken)
        ).thenReturn(true);

        UnauthorizedDataAccessException exception = assertThrows(UnauthorizedDataAccessException.class, () -> sortingGroupService.createSortingGroup(request));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        assertEquals("NOT_PERMITTED", exception.getReason());

        verify(requestAccessToken).getAuthority();

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity)
                .checkIfAuthoritiesMatch(
                        userRepository.findByUserIdAndDeleted(
                                requestAccessToken.getUserId(), false), requestAccessToken);
    }

    @Test
    void deleteSortingGroup_ShouldCallDeleteQueryMethod_WhenChecksPass() {

        int groupId = 69;

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.ADMIN)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        when(sortingGroupValidator.validateId(groupId)).thenReturn(true);

        when(sortingGroupRepository.deleteByGroupId(groupId)).thenReturn(true);

        sortingGroupService.deleteSortingGroup(groupId);

        verify(requestAccessToken).getAuthority();

        verify(sortingGroupValidator).validateId(groupId);

        verify(sortingGroupRepository).deleteByGroupId(groupId);

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);

    }

    @Test
    void deleteSortingGroup_ShouldThrowInvalidIdentificationException_WhenIdCheckFails() {

        int groupId = 69;

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.ADMIN)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        when(sortingGroupValidator.validateId(groupId)).thenThrow(new InvalidIdentificationException());

        InvalidIdentificationException exception = assertThrows(InvalidIdentificationException.class, () -> sortingGroupService.deleteSortingGroup(groupId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        assertEquals("INVALID_IDENTIFIER", exception.getReason());

        verify(requestAccessToken).getAuthority();

        verify(sortingGroupValidator).validateId(groupId);

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);
    }

    @Test
    void deleteSortingGroup_ShouldThrowUnauthorizedDataAccessException_WhenNotAdminAuthority() {

        int groupId = 69;

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.REGULAR)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("REGULAR");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        UnauthorizedDataAccessException exception = assertThrows(UnauthorizedDataAccessException.class, () -> sortingGroupService.deleteSortingGroup(groupId));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        assertEquals("NOT_PERMITTED", exception.getReason());

        verify(requestAccessToken).getAuthority();

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);

    }

    @Test
    void getSortingGroupByName_ShouldReturnGetSortingGroupResponse_whenChecksPass() {

        String groupName = "Dank";

        SortingGroupEntity returnedEntity = SortingGroupEntity.builder().groupId(5).groupName("Dank").groupRank(5).build();

        SortingGroup convertedSortingGroup = SortingGroup.builder().groupId(5).groupName("Dank").groupRank(5).build();

        GetSortingGroupResponse expectedResponse = GetSortingGroupResponse.builder().group(convertedSortingGroup).build();

        when(sortingGroupRepository.existsByGroupName(groupName)).thenReturn(true);

        when(sortingGroupRepository.findByGroupName(groupName)).thenReturn(returnedEntity);

        GetSortingGroupResponse actualResponse = sortingGroupService.getSortingGroupByName(groupName);

        assertEquals(expectedResponse, actualResponse);

        verify(sortingGroupRepository).existsByGroupName(groupName);

        verify(sortingGroupRepository).findByGroupName(groupName);

    }

    @Test
    void getSortingGroupByName_ShouldThrowInvalidIdentificationException_WhenIdCheckFails() {

        String groupName = "Danke";

        when(sortingGroupRepository.existsByGroupName(groupName)).thenReturn(false);

        InvalidIdentificationException exception = assertThrows(InvalidIdentificationException.class, () -> sortingGroupService.getSortingGroupByName(groupName));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

        assertEquals("INVALID_IDENTIFIER", exception.getReason());

        verify(sortingGroupRepository).existsByGroupName(groupName);

    }

    @Test
    void updateSortingGroupName_ShouldThrowDuplicatedSortingGroupNameException_WhenNameIsAlreadyInDB() {

        UpdateSortingGroupNameRequest request = UpdateSortingGroupNameRequest.builder().groupId(5).newGroupName("Danke").build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.REGULAR)
                .build();

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(sortingGroupRepository.existsByGroupName(request.getNewGroupName())).thenReturn(true);

        DuplicatedSortingGroupNameException exception = assertThrows(DuplicatedSortingGroupNameException.class, () -> sortingGroupService.updateSortingGroupName(request));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());

        assertEquals("DUPLICATED_SORTING_GROUP_NAME", exception.getReason());

        verify(sortingGroupRepository).existsByGroupName(request.getNewGroupName());

        verify(requestAccessToken).getAuthority();

        verify(sortingGroupValidator).validateId(request.getGroupId());

        verify(sortingGroupRepository).existsByGroupName(request.getNewGroupName());

//        verify(sortingGroupRepository).updateGroupName(request.getNewGroupName(), request.getGroupId());

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);

    }

    @Test
    void updateSortingGroupName_ShouldUpdateName_WhenChecksPass() {

        //arrange
        UpdateSortingGroupNameRequest request = UpdateSortingGroupNameRequest.builder().groupId(5).newGroupName("Danke").build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.REGULAR)
                .build();

        when(requestAccessToken.getAuthority()).thenReturn("ADMIN");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        when(sortingGroupValidator.validateId(request.getGroupId())).thenReturn(true);

        when(sortingGroupRepository.existsByGroupName(request.getNewGroupName())).thenReturn(false);

        when(sortingGroupRepository.updateGroupName(request.getNewGroupName(), request.getGroupId())).thenReturn(1);

        //act
        sortingGroupService.updateSortingGroupName(request);

        //assert
        verify(sortingGroupValidator).validateId(request.getGroupId());

        verify(sortingGroupRepository).existsByGroupName(request.getNewGroupName());

        verify(sortingGroupRepository).updateGroupName(request.getNewGroupName(), request.getGroupId());

        verify(requestAccessToken).getAuthority();

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);
    }

    @Test
    void updateSortingGroupName_ShouldThrowUnauthorizedDataAccessException_WhenNotAdminAuthority() {

        UpdateSortingGroupNameRequest request = UpdateSortingGroupNameRequest.builder().groupId(5).newGroupName("Danke").build();

        UserEntity user = UserEntity.builder()
                .userId(10)
                .userRole(UserRoleEnum.REGULAR)
                .build();


        when(requestAccessToken.getAuthority()).thenReturn("REGULAR");

        when(requestAccessToken.getUserId()).thenReturn(10);

        when(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false)).thenReturn(user);

        when(accessTokenAuthenticity.checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken)).thenReturn(true);

        UnauthorizedDataAccessException exception = assertThrows(UnauthorizedDataAccessException.class, () -> sortingGroupService.updateSortingGroupName(request));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());

        assertEquals("NOT_PERMITTED", exception.getReason());

        verify(requestAccessToken).getAuthority();

        verify(requestAccessToken, times(3)).getUserId();

        verify(userRepository, times(2)).findByUserIdAndDeleted(requestAccessToken.getUserId(), false);

        verify(accessTokenAuthenticity).checkIfAuthoritiesMatch(userRepository.findByUserIdAndDeleted(requestAccessToken.getUserId(), false), requestAccessToken);

    }
}