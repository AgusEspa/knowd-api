package me.projects.knowd.dtos.requests;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class AreaRequest {

    @NotBlank
    private String title;


    public AreaRequest() {}

    public AreaRequest(String title) {
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
        AreaRequest area = (AreaRequest) o;
        return Objects.equals(title, area.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
