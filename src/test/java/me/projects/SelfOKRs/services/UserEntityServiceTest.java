package me.projects.SelfOKRs.services;

import me.projects.SelfOKRs.dtos.responses.UserResponse;
import me.projects.SelfOKRs.entities.UserEntity;
import me.projects.SelfOKRs.repositories.UserEntityRepository;
import me.projects.SelfOKRs.dtos.requests.RegistrationForm;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserEntityServiceTest {

    @Mock
    UserEntityRepository userEntityRepository;

    @InjectMocks
    UserEntityService userService;

    // Test GET request for all

    // Test GET request for one

    // Test POST request
    @Test
    public void shouldCreateNewUser() {
        RegistrationForm newUser = new RegistrationForm("test1", "test1@mail.com", "testing_pass1");
        UserEntity userEntity = newUser.toUser();

        when(userEntityRepository.save(userEntity)).thenReturn(userEntity);
        UserResponse created = userService.newUser(newUser);

        assertEquals(created.getEmailAddress(), newUser.getEmailAddress());
    }

    @Test
    public void shouldThrowExceptionWhenCreatingNewUserWithInvalidEmail() {
        RegistrationForm newUser = new RegistrationForm("test1", "invalid email", "testing_pass1");
        UserEntity userEntity = newUser.toUser();

        when(userEntityRepository.save(userEntity)).thenReturn(userEntity);
        UserResponse created = userService.newUser(newUser);

        //assertThrows(RuntimeException);
    }

    // Test PUT request

    // Test DELETE request
}
