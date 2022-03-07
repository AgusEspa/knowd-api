package me.projects.knowd.dtos.requests;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class RelationRequest {

    @NotBlank
    private String title;


    public RelationRequest() {}

    public RelationRequest(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationRequest that = (RelationRequest) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
