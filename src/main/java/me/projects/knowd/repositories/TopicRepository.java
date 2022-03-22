package me.projects.knowd.repositories;

import me.projects.knowd.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query(value = "SELECT * FROM TOPICS WHERE USER_ID = ?1", nativeQuery = true)
    List<Topic> findByUserId(Long id);

}
