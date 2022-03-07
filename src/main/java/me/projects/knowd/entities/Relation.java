package me.projects.knowd.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "relations")
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id")
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;


    public Relation() {}

    public Relation(String title, Subject subject) {
        this.title = title;
        this.subject = subject;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relation relation = (Relation) o;
        return Objects.equals(id, relation.id) && Objects.equals(title, relation.title) && Objects.equals(subject, relation.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subject);
    }
}
