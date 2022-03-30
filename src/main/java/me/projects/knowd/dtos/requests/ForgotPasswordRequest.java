package me.projects.knowd.dtos.requests;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Email;

public class ForgotPasswordRequest {

    @Email(message = "Not a valid email address")
    private String emailAddress;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ForgotPasswordRequest(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
