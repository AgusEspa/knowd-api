package me.projects.knowd.entities;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @Min(1)
    @Max(100)
    private int progress;

    @NotBlank
    private String status;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public Topic() {}

    public Topic(String title, int progress, String status, Subject subject, UserEntity user) {
        this.title = title;
        this.progress = progress;
        this.status = status;
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
