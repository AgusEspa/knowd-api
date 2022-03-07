package me.projects.knowd.exceptions;

public class ObjectiveNotFoundException extends RuntimeException {

    public ObjectiveNotFoundException(Long id) {
        super("Could not find Objective " + id);
    }
}
