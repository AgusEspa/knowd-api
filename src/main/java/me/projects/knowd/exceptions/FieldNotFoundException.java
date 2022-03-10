package me.projects.knowd.exceptions;

public class FieldNotFoundException extends RuntimeException {

    public FieldNotFoundException(Long id) {
        super("Could not find field " + id);
    }
}
