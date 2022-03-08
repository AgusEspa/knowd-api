package me.projects.knowd.dtos.responses;

import java.util.Objects;

public class TopicResponse {

    private Long id;

    private String title;

    private int progress;

    private String status;


    public TopicResponse() {}

    public TopicResponse(Long id, String title, int progress, String status) {
        this.id = id;
        this.title = title;
        this.progress = progress;
        this.status = status;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
