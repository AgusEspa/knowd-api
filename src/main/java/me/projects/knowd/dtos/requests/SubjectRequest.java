package me.projects.knowd.dtos.requests;

import me.projects.knowd.exceptions.CustomMethodArgumentNotValidException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class SubjectRequest {

    private String title;

    private String field;

    private String area;

    @NotNull
    @Min(value = 1, message = "Number must be more than 0")
    @Max(value = 10, message = "Number must be less or equal to 10")
    private int relevance;

    @NotNull
    @Min(value = 1, message = "Number must be more than 0")
    @Max(value = 100, message = "Number must be less or equal to 100")
    private int progress;

    @NotNull(message = "Status must not be empty")
    private String status;

    private boolean needsAttention;

    private LocalDate dueDate;


    public SubjectRequest() {}

    public SubjectRequest(String title, String field, String area, int relevance, int progress, String status, boolean needsAttention, String dueDate) {
        this.title = title;
        this.field = field;
        this.area = area;
        this.relevance = relevance;
        this.progress = progress;
        this.status = status;
        this.needsAttention = needsAttention;
        if (dueDate == null || dueDate.isEmpty()) {
            this.dueDate = null;
        } else {
            try { this.dueDate = LocalDate.parse(dueDate); }
            catch (Exception e) {
                throw new CustomMethodArgumentNotValidException("Bad date format - must be yyyy-mm-dd");
            }
        }
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectRequest that = (SubjectRequest) o;
        return Objects.equals(title, that.title) && Objects.equals(field, that.field) && Objects.equals(area, that.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, field, area);
    }
}
