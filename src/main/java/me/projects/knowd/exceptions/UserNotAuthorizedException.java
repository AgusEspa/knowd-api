package me.projects.knowd.exceptions;

public class UserNotAuthorizedException extends RuntimeException {

    public UserNotAuthorizedException(String username) {
        super("User " + username + " is not authorized");
    }
}
