package me.projects.knowd.dtos.requests;

import me.projects.knowd.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class RegistrationForm {

    @NotNull
    @Size(
            min = 3,
            max = 255,
            message = "Username must be at least 3 characters"
    )
    private String username;

    @Email(message = "Not a valid email address")
    private String emailAddress;

    @NotNull
    @Size(
            min = 8,
            max = 255,
            message = "Password must be at least 8 characters long"
    )
    private String password;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public RegistrationForm(String username, String emailAddress, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public UserEntity toUser() {
        return new UserEntity(username, emailAddress, passwordEncoder.encode(password));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof RegistrationForm || o instanceof UserEntity)) return false;
        RegistrationForm that = (RegistrationForm) o;
        return Objects.equals(username, that.username) && Objects.equals(emailAddress, that.emailAddress) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, emailAddress, password);
    }
}
