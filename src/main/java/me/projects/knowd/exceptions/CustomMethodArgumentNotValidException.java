package me.projects.knowd.exceptions;

public class CustomMethodArgumentNotValidException extends RuntimeException {

    public CustomMethodArgumentNotValidException(String message) {
        super(message);
    }
}
