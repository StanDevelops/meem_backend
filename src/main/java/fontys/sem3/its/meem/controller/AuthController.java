package fontys.sem3.its.meem.controller;

import fontys.sem3.its.meem.business.usecase.User.LoginUserUseCase;
import fontys.sem3.its.meem.domain.request.Auth.LoginRequest;
import fontys.sem3.its.meem.domain.response.Auth.UserLoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//done
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000/"}, allowCredentials = "true", originPatterns = "https://web.postman.*")
public class AuthController {
    LoginUserUseCase loginUserUseCase;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(loginUserUseCase.LogInUser(request));
    }

}
