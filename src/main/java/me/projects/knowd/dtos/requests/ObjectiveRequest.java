package me.projects.knowd.dtos.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ObjectiveRequest {

    @NotBlank(message = "Title must not be empty")
    private String title;

    @Min(value = 1, message = "Number must be more than 0")
    @Max(value = 5, message = "Number must be less than 6")
    private int importance;


    private ObjectiveRequest() {}

    public ObjectiveRequest(String title, int importance) {
        this.title = title;
        this.importance = importance;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}
