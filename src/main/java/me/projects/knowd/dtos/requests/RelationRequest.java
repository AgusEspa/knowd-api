package me.projects.knowd.dtos.requests;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class RelationRequest {

    @NotNull
    private String title;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
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
