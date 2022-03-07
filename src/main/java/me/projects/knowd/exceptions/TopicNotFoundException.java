package me.projects.knowd.exceptions;

public class TopicNotFoundException extends RuntimeException {

    public TopicNotFoundException(Long id) {
        super("Could not find topic " + id);
    }
}
