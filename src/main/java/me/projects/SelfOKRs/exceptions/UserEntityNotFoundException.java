package me.projects.SelfOKRs.exceptions;

public class UserEntityNotFoundException extends RuntimeException {

    public UserEntityNotFoundException(Long id) {
        super("Could not find User " + id);
    }

    public UserEntityNotFoundException(String username) {
        super("Could not find user with " + username + " email");
    }
}
