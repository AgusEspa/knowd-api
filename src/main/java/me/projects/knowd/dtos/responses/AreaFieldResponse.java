package me.projects.knowd.dtos.responses;

import java.util.Objects;

public class AreaFieldResponse {

    private Long id;

    private String title;


    public AreaFieldResponse() {}

    public AreaFieldResponse(Long id, String title) {
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
        AreaFieldResponse that = (AreaFieldResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
