package me.projects.knowd.dtos.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ResetPasswordRequest {

    @Size(
            min = 8,
            max = 255,
            message = "Password must be at least 8 characters long"
    )
    private String newPassword;

    @NotNull
    String passwordToken;

    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String newPassword, String passwordToken) {
        this.newPassword = newPassword;
        this.passwordToken = passwordToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordToken() {
        return passwordToken;
    }

    public void setPasswordToken(String passwordToken) {
        this.passwordToken = passwordToken;
    }
}
