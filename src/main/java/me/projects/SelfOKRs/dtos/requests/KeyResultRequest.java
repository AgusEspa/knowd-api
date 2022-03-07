package me.projects.SelfOKRs.dtos.requests;

import me.projects.SelfOKRs.exceptions.CustomMethodArgumentNotValidException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class KeyResultRequest {

    @NotBlank(message = "Title must not be empty")
    private String title;

    private LocalDate dueDate;

    @NotNull
    private Boolean isDone;

    public KeyResultRequest() {
    }

    public KeyResultRequest(String title, String dueDate, Boolean isDone) {
        this.title = title;
        if (dueDate == null || dueDate.isEmpty()) {
            this.dueDate = null;
        } else {
            try { this.dueDate = LocalDate.parse(dueDate); }
            catch (Exception e) {
                throw new CustomMethodArgumentNotValidException("Bad date format - must be yyyy-mm-dd");
            }
        }
        this.isDone = isDone;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyResultRequest that = (KeyResultRequest) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
