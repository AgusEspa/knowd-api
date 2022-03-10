package me.projects.knowd.exceptions;

public class AreaNotFoundException extends RuntimeException {

    public AreaNotFoundException(Long id) {
        super("Could not find area " + id);
    }
}
