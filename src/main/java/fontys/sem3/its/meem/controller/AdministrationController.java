package fontys.sem3.its.meem.controller;

import fontys.sem3.its.meem.business.usecase.User.GiveUserModeratorPrivilegesUseCase;
import fontys.sem3.its.meem.business.usecase.User.SetUserAsBannedUseCase;
import fontys.sem3.its.meem.business.usecase.User.TakeAwayUserModeratorPrivilegesUseCase;
import fontys.sem3.its.meem.config.security.isauthenticated.IsAuthenticated;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/administration")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/"})
public class AdministrationController {
    GiveUserModeratorPrivilegesUseCase giveUserModeratorPrivilegesUseCase;
    SetUserAsBannedUseCase setUserAsBannedUseCase;
    TakeAwayUserModeratorPrivilegesUseCase takeAwayUserModeratorPrivilegesUseCase;

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PutMapping("/mod")
    public ResponseEntity<Boolean> makeUserMod(@RequestParam @Valid int userId) {
        giveUserModeratorPrivilegesUseCase.makeUserAModerator(userId);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_ADMIN"})
    @PutMapping("/unmod")
    public ResponseEntity<Boolean> takeAwayMod(@RequestParam @Valid int userId) {
        takeAwayUserModeratorPrivilegesUseCase.removeUserModPrivileges(userId);
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
