package me.projects.SelfOKRs.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.projects.SelfOKRs.dtos.requests.DeleteUserForm;
import me.projects.SelfOKRs.dtos.requests.EditUserForm;
import me.projects.SelfOKRs.dtos.requests.ResetPasswordRequest;
import me.projects.SelfOKRs.dtos.responses.UserCredentialsResponse;
import me.projects.SelfOKRs.dtos.responses.UserResponse;
import me.projects.SelfOKRs.entities.UserEntity;
import me.projects.SelfOKRs.exceptions.*;
import me.projects.SelfOKRs.repositories.UserEntityRepository;
import me.projects.SelfOKRs.dtos.requests.RegistrationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class UserEntityService {

    private final UserEntityRepository userEntityRepository;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    Logger logger = LoggerFactory.getLogger(UserEntityService.class);

    @Autowired
    public UserEntityService(UserEntityRepository userEntityRepository, EmailService emailService) {
        this.userEntityRepository = userEntityRepository;
        this.emailService = emailService;
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

    public void sendPasswordToken(String passwordToken, String emailAddress) {
        String resetLink = "To set a new password, please follow this link or copy/paste it into your browser: " + "http://localhost:3000/reset_password?token=" + passwordToken;
        emailService.sendEmail(emailAddress, "Self.OKRs Help Desk <knowd.help@gmail.com>", "Password reset", resetLink);
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
