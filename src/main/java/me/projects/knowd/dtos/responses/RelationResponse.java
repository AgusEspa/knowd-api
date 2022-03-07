package me.projects.knowd.dtos.responses;

import me.projects.knowd.entities.Subject;
import me.projects.knowd.entities.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class RelationResponse {

    private Long id;

    private String title;


    public RelationResponse() {}

    public RelationResponse(Long id, String title) {
        this.id = id;
        this.title = title;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationResponse relation = (RelationResponse) o;
        return Objects.equals(id, relation.id) && Objects.equals(title, relation.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
