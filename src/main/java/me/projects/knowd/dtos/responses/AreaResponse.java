package me.projects.knowd.dtos.responses;

import java.util.Objects;

public class AreaResponse {

    private Long id;

    private String title;

    private Long fieldId;


    public AreaResponse() {}

    public AreaResponse(Long id, String title, Long fieldId) {
        this.id = id;
        this.title = title;
        this.fieldId = fieldId;
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

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaResponse that = (AreaResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
