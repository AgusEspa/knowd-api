package me.projects.SelfOKRs.exceptions;

public class CustomMethodArgumentNotValidException extends RuntimeException {

    public CustomMethodArgumentNotValidException(String message) {
        super(message);
    }
}
