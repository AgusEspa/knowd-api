package me.projects.knowd.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "areas")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_id")
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public Area() {}

    public Area(String title, Field field, UserEntity user) {
        this.title = title;
        this.field = field;
        this.user = user;
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

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return Objects.equals(id, area.id) && Objects.equals(title, area.title) && Objects.equals(field, area.field) && Objects.equals(user, area.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, field, user);
    }
}
