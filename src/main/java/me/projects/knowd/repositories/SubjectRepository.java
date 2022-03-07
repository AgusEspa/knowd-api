package me.projects.knowd.repositories;

import me.projects.knowd.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query(value = "SELECT * FROM SUBJECTS WHERE USER_ID = ?1", nativeQuery = true)
    List<Subject> findByUserId(Long id);

}
