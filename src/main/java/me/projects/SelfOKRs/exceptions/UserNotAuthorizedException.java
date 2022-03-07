package me.projects.SelfOKRs.exceptions;

public class UserNotAuthorizedException extends RuntimeException {

    public UserNotAuthorizedException(String username) {
        super("User " + username + " is not authorized");
    }
}
