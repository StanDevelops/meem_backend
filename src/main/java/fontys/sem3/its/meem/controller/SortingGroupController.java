package fontys.sem3.its.meem.controller;

import fontys.sem3.its.meem.business.usecase.SortingGroup.*;
import fontys.sem3.its.meem.config.security.isauthenticated.IsAuthenticated;
import fontys.sem3.its.meem.domain.request.SortingGroup.CreateSortingGroupRequest;
import fontys.sem3.its.meem.domain.request.SortingGroup.UpdateSortingGroupNameRequest;
import fontys.sem3.its.meem.domain.response.SortingGroup.GetSortingGroupResponse;
import fontys.sem3.its.meem.domain.response.SortingGroup.GetSortingGroupsResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

//done
@RestController
@RequestMapping("/api/groups")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/")
public class SortingGroupController {
    private final GetSortingGroupsUseCase getSortingGroupsUseCase;
    private final GetSortingGroupUseCase getSortingGroupUseCase;
    private final CreateSortingGroupUseCase createSortingGroupUseCase;
    private final UpdateSortingGroupNameUseCase updateSortingGroupNameUseCase;
    private final DeleteSortingGroupUseCase deleteSortingGroupUseCase;

    @GetMapping
    public ResponseEntity<GetSortingGroupsResponse> getSortingGroups() {
        return ResponseEntity.ok(getSortingGroupsUseCase.getSortingGroups());
    }

    @GetMapping("/name/{groupName}")
    public ResponseEntity<GetSortingGroupResponse> getSortingGroupByName(@PathVariable @Valid final @NotBlank String groupName) {
        return ResponseEntity.ok(getSortingGroupUseCase.getSortingGroupByName(groupName));
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity createSortingGroup(@RequestBody @Valid @NotBlank String groupName) {
        CreateSortingGroupRequest request = CreateSortingGroupRequest.builder().groupName(groupName).build();
        createSortingGroupUseCase.createSortingGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * @param groupId   must be >0
     * @param groupName
     * @return response when ....
     */
    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PutMapping("{groupId}")
    public ResponseEntity updateSortingGroup(@PathVariable @Valid int groupId, @RequestBody @Valid @NotBlank String groupName) {
        UpdateSortingGroupNameRequest request = UpdateSortingGroupNameRequest.builder().groupId(groupId).newGroupName(groupName).build();
        updateSortingGroupNameUseCase.updateSortingGroupName(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @DeleteMapping("{groupId}")
    public ResponseEntity deleteSortingGroup(@PathVariable @Valid int groupId) {
        deleteSortingGroupUseCase.deleteSortingGroup(groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
