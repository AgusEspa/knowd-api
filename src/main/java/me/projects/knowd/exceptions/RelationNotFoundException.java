package me.projects.knowd.exceptions;

public class RelationNotFoundException extends RuntimeException {

    public RelationNotFoundException(Long id) {
        super("Could not find relation " + id);
    }
}
