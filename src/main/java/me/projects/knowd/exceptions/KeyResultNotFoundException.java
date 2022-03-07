package me.projects.knowd.exceptions;

public class KeyResultNotFoundException extends RuntimeException {

    public KeyResultNotFoundException(Long id) {
        super("Could not find key result " + id);
    }
}
