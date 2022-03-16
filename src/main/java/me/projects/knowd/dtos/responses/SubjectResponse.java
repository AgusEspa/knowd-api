package me.projects.knowd.dtos.responses;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class SubjectResponse {

    private Long id;

    private String title;

    private String field;

    private String area;

    private int relevance;

    private int progress;

    private String status;

    private boolean needsAttention;

    private LocalDate dueDate;

    private List<RelationResponse> relations;


    public SubjectResponse() {}

    public SubjectResponse(Long id, String title, String field, String area, int relevance, int progress, String status, boolean needsAttention, LocalDate dueDate, List<RelationResponse> relations) {
        this.id = id;
        this.title = title;
        this.field = field;
        this.area = area;
        this.relevance = relevance;
        this.progress = progress;
        this.status = status;
        this.needsAttention = needsAttention;
        this.dueDate = dueDate;
        this.relations = relations;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public List<RelationResponse> getRelations() {
        return relations;
    }

    public void setRelations(List<RelationResponse> relations) {
        this.relations = relations;
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
