package me.projects.knowd.dtos.requests;

import javax.validation.constraints.Email;

public class ForgotPasswordRequest {

    @Email(message = "Not a valid email address")
    private String emailAddress;


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
