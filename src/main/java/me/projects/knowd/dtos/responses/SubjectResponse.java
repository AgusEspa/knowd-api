package me.projects.knowd.dtos.responses;

import me.projects.knowd.entities.Relation;
import me.projects.knowd.entities.Topic;
import me.projects.knowd.entities.UserEntity;
import me.projects.knowd.tools.Status;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class SubjectResponse {

    private Long id;

    private String title;

    private String field;

    private String area;

    private Set<Topic> topics;

    private Set<Relation> relations;

    private int relevance;

    private int progress;

    private Status status;

    private boolean needsAttention;

    private LocalDate dueDate;


    public SubjectResponse() {}

    public SubjectResponse(Long id, String title, String field, String area, Set<Topic> topics, Set<Relation> relations, int relevance, int progress, Status status, boolean needsAttention, LocalDate dueDate) {
        this.id = id;
        this.title = title;
        this.field = field;
        this.area = area;
        this.topics = topics;
        this.relations = relations;
        this.relevance = relevance;
        this.progress = progress;
        this.status = status;
        this.needsAttention = needsAttention;
        this.dueDate = dueDate;
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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Set<Relation> getRelations() {
        return relations;
    }

    public void setRelations(Set<Relation> relations) {
        this.relations = relations;
    }

    public int getRelevance() {
        return relevance;
    }

    public void setRelevance(int relevance) {
        this.relevance = relevance;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean getNeedsAttention() {
        return needsAttention;
    }

    public void setNeedsAttention(boolean needsAttention) {
        this.needsAttention = needsAttention;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectResponse that = (SubjectResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(field, that.field) && Objects.equals(area, that.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, field, area);
    }
}
