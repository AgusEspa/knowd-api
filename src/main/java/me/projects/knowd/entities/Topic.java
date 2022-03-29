package me.projects.knowd.entities;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private boolean isDone;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    Topic() {}

    public Topic(String title, boolean isDone, Subject subject, UserEntity user) {
        this.title = title;
        this.isDone = isDone;
        this.subject = subject;
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

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        isDone = done;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
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
        Topic topic = (Topic) o;
        return Objects.equals(id, topic.id) && Objects.equals(title, topic.title) && Objects.equals(subject, topic.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, subject);
    }
}
