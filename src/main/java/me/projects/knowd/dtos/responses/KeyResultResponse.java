package me.projects.knowd.dtos.responses;

import java.time.LocalDate;

public class KeyResultResponse {

    private Long id;

    private String title;

    private LocalDate dueDate;

    private Boolean isDone;


    KeyResultResponse() {}

    public KeyResultResponse(Long id, String title, LocalDate dueDate, Boolean isDone) {
        this.id = id;
        this.title = title;
        this.dueDate = dueDate;
        this.isDone = isDone;
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

}
