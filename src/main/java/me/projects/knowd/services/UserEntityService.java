package me.projects.knowd.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import me.projects.knowd.dtos.requests.DeleteUserForm;
import me.projects.knowd.dtos.requests.EditUserForm;
import me.projects.knowd.dtos.requests.ResetPasswordRequest;
import me.projects.knowd.dtos.responses.UserCredentialsResponse;
import me.projects.knowd.dtos.responses.UserResponse;
import me.projects.knowd.entities.UserEntity;
import me.projects.knowd.exceptions.*;
import me.projects.knowd.repositories.UserEntityRepository;
import me.projects.knowd.dtos.requests.RegistrationForm;
import me.projects.knowd.security.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;

    private final TokenService tokenService;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(UserEntityService.class);

    @Value("${VIEW_BASE_URL}")
    private String viewBaseUrl;

    public UserEntityService(UserEntityRepository userEntityRepository, TokenService tokenService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserCredentialsResponse fetchUserData() {

        String username = getUsername();

        UserEntity fetchedUser = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));
        return new UserCredentialsResponse(fetchedUser.getUsername(), fetchedUser.getEmailAddress());
    }

    public UserResponse newUser(RegistrationForm newUser) {
        if (userEntityRepository.findByEmailAddress(newUser.getEmailAddress()).isEmpty()) {
            UserEntity userEntity = newUser.toUser();
            userEntityRepository.save(userEntity);
            UserResponse userResponse = new UserResponse(userEntity.getId(), userEntity.getUsername(), userEntity.getEmailAddress());
            return userResponse;
        } else {
            throw new UserAlreadyExistsException(newUser.getEmailAddress());
        }
    }

    public UserResponse updateUser(EditUserForm editedUser) {

        String username = getUsername();

        UserEntity fetchedUser = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));

        if (!(passwordEncoder.matches(editedUser.getOldPassword(), fetchedUser.getPassword()))) {
            throw new WrongPasswordException();
        }

        if (editedUser.getEmailAddress().equals(fetchedUser.getEmailAddress()) || userEntityRepository.findByEmailAddress(editedUser.getEmailAddress()).isEmpty()) {

            fetchedUser.setUsername(editedUser.getUsername());

            fetchedUser.setEmailAddress(editedUser.getEmailAddress());

            fetchedUser.setPassword(passwordEncoder.encode(editedUser.getNewPassword()));

            userEntityRepository.save(fetchedUser);
            UserResponse userResponse = new UserResponse(fetchedUser.getId(), fetchedUser.getUsername(), fetchedUser.getEmailAddress());
            return userResponse;
        } else {
            throw new UserAlreadyExistsException(editedUser.getEmailAddress());
        }
    }

    public void removeUser(DeleteUserForm deleteUserForm) {

        String username = getUsername();

        UserEntity fetchedUser = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));

        if (username.equals(deleteUserForm.getEmailAddress())) {
            if (passwordEncoder.matches(deleteUserForm.getOldPassword(), fetchedUser.getPassword())) {
                userEntityRepository.deleteById(fetchedUser.getId());

            } else throw new WrongPasswordException();

        } else throw new UserNotAuthorizedException(username);

    }

    public void sendPasswordToken(String emailAddress) throws IOException {

        String passwordToken = tokenService.generatePasswordToken(emailAddress);

        String resetLink = "To set a new password, please follow this link or copy/paste it into your browser (it will expire in 10 minutes): " + viewBaseUrl + "/reset_password?token=" + passwordToken;
        emailService.sendEmail(emailAddress, "Knowd Help Desk <knowd.help@gmail.com>", "Password reset", resetLink);
        logger.info("Sending reset password token to " + emailAddress);
    }

    public UserResponse setNewPassword(ResetPasswordRequest resetPasswordRequest) {

        try {
            String token = resetPasswordRequest.getPasswordToken();
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String username = decodedJWT.getSubject();

            UserEntity fetchedUser = userEntityRepository.findByEmailAddress(username)
                    .orElseThrow(() -> new UserEntityNotFoundException(username));

            fetchedUser.setUsername(fetchedUser.getUsername());
            fetchedUser.setEmailAddress(fetchedUser.getEmailAddress());
            fetchedUser.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));

            userEntityRepository.save(fetchedUser);

            UserResponse userResponse = new UserResponse(fetchedUser.getId(), fetchedUser.getUsername(), fetchedUser.getEmailAddress());
            return userResponse;

        } catch (Exception e) {
            throw new InvalidTokenException(e.getMessage());
        }
    }

    protected String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
