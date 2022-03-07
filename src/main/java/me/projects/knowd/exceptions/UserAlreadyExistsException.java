package me.projects.knowd.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String emailAddress) {
        super("This email address is already registered");
    }
}
