package me.projects.knowd.dtos.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class TopicRequest {

    @NotBlank(message = "Title must not be empty")
    private String title;

    @NotNull
    @Min(value = 1, message = "Number must be more than 0")
    @Max(value = 100, message = "Number must be less or equal to 100")
    private int progress;

    @NotNull(message = "Status must not be empty")
    private String status;


    public TopicRequest() {}

    public TopicRequest(String title, int progress, String status) {
        this.title = title;
        this.progress = progress;
        this.status = status;
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
        TopicRequest that = (TopicRequest) o;
        return progress == that.progress && Objects.equals(title, that.title) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, progress, status);
    }
}
