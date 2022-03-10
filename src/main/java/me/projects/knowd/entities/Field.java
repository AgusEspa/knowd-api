package me.projects.knowd.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "fields")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank
    private String title;

    @OneToMany(mappedBy = "field", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Area> areas = new HashSet<>();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public Field() {}

    public Field(String title, UserEntity user) {
        this.title = title;
        this.user = user;
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
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
        Field field = (Field) o;
        return Objects.equals(Id, field.Id) && Objects.equals(title, field.title) && Objects.equals(areas, field.areas) && Objects.equals(user, field.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, title, areas, user);
    }
}
