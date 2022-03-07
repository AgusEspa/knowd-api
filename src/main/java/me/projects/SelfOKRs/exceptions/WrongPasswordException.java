package me.projects.SelfOKRs.exceptions;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("Wrong password");
    }
}
