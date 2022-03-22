package me.projects.knowd.dtos.responses;

import java.util.Objects;

public class TopicResponse {

    private Long id;

    private String title;

    private boolean isDone;


    public TopicResponse() {}

    public TopicResponse(Long id, String title, boolean isDone) {
        this.id = id;
        this.title = title;
        this.isDone = isDone;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicResponse topic = (TopicResponse) o;
        return Objects.equals(id, topic.id) && Objects.equals(title, topic.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
