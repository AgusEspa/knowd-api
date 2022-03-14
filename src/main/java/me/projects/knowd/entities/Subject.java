package me.projects.knowd.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    private String title;

    private String field;

    private String area;

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Topic> topics = new HashSet<>();

    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Relation> relations = new HashSet<>();

    @NotNull
    @Min(1)
    @Max(10)
    private int relevance;

    @NotNull
    @Min(1)
    @Max(100)
    private int progress;

    @NotBlank
    private String status;

    @NotNull
    private boolean needsAttention;

    private LocalDate dueDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public Subject() {}

    public Subject(String title, String field, String area, int relevance, int progress, String status, boolean needsAttention, LocalDate dueDate, UserEntity user) {
        this.title = title;
        this.field = field;
        this.area = area;
        this.relevance = relevance;
        this.progress = progress;
        this.status = status;
        this.needsAttention = needsAttention;
        this.dueDate = dueDate;
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

    public Set<Topic> getTopics() {
        return topics;
    }

    public Set<Relation> getRelations() {
        return relations;
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
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) && Objects.equals(title, subject.title) && Objects.equals(field, subject.field) && Objects.equals(area, subject.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, field, area);
    }
}
