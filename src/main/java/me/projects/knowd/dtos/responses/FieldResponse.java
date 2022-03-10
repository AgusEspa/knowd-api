package me.projects.knowd.dtos.responses;

import java.util.List;
import java.util.Objects;

public class FieldResponse {

    private Long id;

    private String title;

    private List<AreaResponse> areas;


    public FieldResponse() {}

    public FieldResponse(Long id, String title, List<AreaResponse> areas) {
        this.id = id;
        this.title = title;
        this.areas = areas;
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

    public List<AreaResponse> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaResponse> areas) {
        this.areas = areas;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldResponse that = (FieldResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(areas, that.areas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, areas);
    }
}
