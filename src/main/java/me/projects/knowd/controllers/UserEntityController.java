package me.projects.knowd.controllers;

import me.projects.knowd.dtos.requests.*;
import me.projects.knowd.dtos.responses.UserCredentialsResponse;
import me.projects.knowd.dtos.responses.UserResponse;
import me.projects.knowd.security.TokenService;
import me.projects.knowd.services.UserEntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserEntityController {

    private final UserEntityService userEntityService;
    private final TokenService tokenService;

    Logger logger = LoggerFactory.getLogger(UserEntityController.class);

    public UserEntityController(UserEntityService userEntityService, TokenService tokenService) {
        this.userEntityService = userEntityService;
        this.tokenService = tokenService;
    }

    @GetMapping("/authenticated")
    ResponseEntity<UserCredentialsResponse> getUserData() {
        return ResponseEntity.ok(userEntityService.fetchUserData());
    }

    @PostMapping(path = "/signup")
    ResponseEntity<UserResponse> createUser(@Valid @RequestBody RegistrationForm newUser) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userEntityService.newUser(newUser));
    }

    @PutMapping
    ResponseEntity<UserResponse> replaceUser(@Valid @RequestBody EditUserForm editedUser) {
        return ResponseEntity.ok(userEntityService.updateUser(editedUser));
    }

    @DeleteMapping
    void deleteUser(@Valid @RequestBody DeleteUserForm deleteUserForm) {
        userEntityService.removeUser(deleteUserForm);
    }

    @GetMapping("/token/refresh")
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        tokenService.refreshToken(request, response);
    }

    @PostMapping("/sendPasswordReset")
    void getPasswordToken(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String passwordToken = tokenService.generatePasswordToken(forgotPasswordRequest.getEmailAddress());
        userEntityService.sendPasswordToken(passwordToken, forgotPasswordRequest.getEmailAddress());
    }

    @PatchMapping("/reset_password")
    ResponseEntity<UserResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(userEntityService.setNewPassword(resetPasswordRequest));
    }
}
