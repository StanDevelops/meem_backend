package fontys.sem3.its.meem.controller;

import fontys.sem3.its.meem.business.usecase.User.*;
import fontys.sem3.its.meem.business.usecase.UserRelation.CreateUserRelationUseCase;
import fontys.sem3.its.meem.business.usecase.UserRelation.GetUserRelationsUseCase;
import fontys.sem3.its.meem.config.security.isauthenticated.IsAuthenticated;
import fontys.sem3.its.meem.domain.model.FrontendUserRelation;
import fontys.sem3.its.meem.domain.request.User.ChangePasswordRequest;
import fontys.sem3.its.meem.domain.request.User.ChangeUsernameRequest;
import fontys.sem3.its.meem.domain.request.User.CreateUserRequest;
import fontys.sem3.its.meem.domain.request.UserRelation.CreateUserRelationRequest;
import fontys.sem3.its.meem.domain.response.User.GetUserProfilePictureResponse;
import fontys.sem3.its.meem.domain.response.User.GetUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

//done
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/"}, originPatterns = "https://web.postman.*")
public class UserController {
    GetUserUseCase getUserUseCase;
    CreateUserUseCase createUserUseCase;
    DeleteUserUseCase deleteUserUseCase;
    ChangeUsernameUseCase changeUsernameUseCase;
    ChangeUserPasswordUseCase changeUserPasswordUseCase;
    GetUserProfilePictureUseCase getUserProfilePictureUseCase;
    GetUserRelationsUseCase getUserRelationsUseCase;
    CreateUserRelationUseCase createUserRelationUseCase;

//    @CrossOrigin(allowCredentials = "true")
//    @IsAuthenticated
//    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_BANNED", "ROLE_ADMIN"})
//    @GetMapping("/id")
//    public ResponseEntity<GetUserResponse> getUserById(@RequestParam @Valid final int userId) {
//        return ResponseEntity.ok(getUserUseCase.getUserById(userId));
//    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_BANNED", "ROLE_ADMIN"})
    @GetMapping("/username")
    public ResponseEntity<GetUserResponse> getUserByUsername(@RequestParam @Valid final String username) {
        return ResponseEntity.ok(getUserUseCase.getUserByUsername(username));
    }

    @CrossOrigin(allowCredentials = "true")
//    @IsAuthenticated
//    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_BANNED"})
    @GetMapping("/pfp")
    public ResponseEntity<GetUserProfilePictureResponse> getUserPFP(@RequestParam @Valid final int userId) {
        return ResponseEntity.ok(getUserProfilePictureUseCase.getUserPFP(userId));
    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_BANNED", "ROLE_ADMIN"})
    @GetMapping("/relations")
    public ResponseEntity<List<FrontendUserRelation>> getUserRelations(@RequestParam @Valid final String username, @RequestParam String relationType) {
        return ResponseEntity.ok(getUserRelationsUseCase.getUserRelationStatusByType(username, relationType));
    }

    @CrossOrigin(allowCredentials = "true")
    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_BANNED", "ROLE_ADMIN"})
    @PostMapping("/relations")
    public ResponseEntity<FrontendUserRelation> createUserRelations(@RequestBody CreateUserRelationRequest request) {
        return ResponseEntity.ok(createUserRelationUseCase.createUserRelation(request));
    }

    //    @PostMapping
//    public ResponseEntity<Boolean> createUser(@RequestParam @Valid String username,
//                                              @RequestParam @Valid String email,
//                                              @RequestParam @Valid String password,
//                                              @RequestParam @Valid Character gender) {
//        CreateUserRequest request = CreateUserRequest.builder()
//                .username(username)
//                .email(email)
//                .password(URLDecoder.decode(password, StandardCharsets.UTF_8))
//                .gender(gender)
//                .build();
    @PostMapping
    public ResponseEntity<Boolean> createUser(@RequestBody CreateUserRequest request) {
        createUserUseCase.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN"})
    @PutMapping("/new-username")
    public ResponseEntity<Boolean> updateUsername(@RequestBody @Valid ChangeUsernameRequest request) {
        changeUsernameUseCase.changeUsername(request);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN", "ROLE_BANNED"})
    @PutMapping("/change-password")
    public ResponseEntity<Boolean> updatePassword(@RequestBody @Valid ChangePasswordRequest request) {
        changeUserPasswordUseCase.changePassword(request);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }


    @IsAuthenticated
    @RolesAllowed({"ROLE_REGULAR", "ROLE_MOD", "ROLE_ADMIN", "ROLE_BANNED"})
    @DeleteMapping
    public ResponseEntity deleteUser(@RequestParam @Valid int userId) {
        deleteUserUseCase.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
