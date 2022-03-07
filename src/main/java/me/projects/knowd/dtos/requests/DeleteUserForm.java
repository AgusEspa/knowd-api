package me.projects.knowd.dtos.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class DeleteUserForm {

    @Email(message = "Not a valid email address")
    private String emailAddress;

    @NotNull
    private String oldPassword;

    public DeleteUserForm() {
    }

    public DeleteUserForm(String emailAddress, String oldPassword) {
        this.emailAddress = emailAddress;
        this.oldPassword = oldPassword;
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
}
