package me.projects.SelfOKRs.exceptions;

public class KeyResultNotFoundException extends RuntimeException {

    public KeyResultNotFoundException(Long id) {
        super("Could not find key result " + id);
    }
}
