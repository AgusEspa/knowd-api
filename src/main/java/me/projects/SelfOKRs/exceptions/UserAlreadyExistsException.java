package me.projects.SelfOKRs.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String emailAddress) {
        super("This email address is already registered");
    }
}
