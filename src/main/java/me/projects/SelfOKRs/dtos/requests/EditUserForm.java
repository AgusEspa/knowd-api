package me.projects.SelfOKRs.dtos.requests;

import me.projects.SelfOKRs.entities.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class EditUserForm {

    @Size(
            min = 3,
            max = 255,
            message = "Name must be longer than 2 characters"
    )
    private String username;

    @Email(message = "Not a valid email address")
    private String emailAddress;

    @NotNull
    private String oldPassword;

    @Size(
            min = 8,
            max = 255,
            message = "Password must be at least 8 characters long"
    )
    private String newPassword;

    private final PasswordEncoder passwordEncoder;

    public EditUserForm(String username, String emailAddress, String oldPassword, String newPassword) {
        this.username = username;
        if (emailAddress.isEmpty()) this.emailAddress = SecurityContextHolder.getContext().getAuthentication().getName();
        else this.emailAddress = emailAddress;
        this.oldPassword = oldPassword;
        if (newPassword.isEmpty()) this.newPassword = oldPassword;
        else this.newPassword = newPassword;
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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public UserEntity toUser() {
        return new UserEntity(username, emailAddress, passwordEncoder.encode(newPassword));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EditUserForm that = (EditUserForm) o;
        return Objects.equals(username, that.username) && Objects.equals(emailAddress, that.emailAddress) && Objects.equals(newPassword, that.newPassword) && Objects.equals(oldPassword, that.oldPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, emailAddress, oldPassword, newPassword);
    }
}
