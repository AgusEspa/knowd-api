package me.projects.knowd.repositories;

import me.projects.knowd.entities.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RelationRepository extends JpaRepository<Relation, Long> {

    @Query(value = "SELECT * FROM RELATIONS WHERE SUBJECT_ID = ?1", nativeQuery = true)
    List<Relation> findAllPerSubject(Long subjectId);

    @Query(value = "SELECT * FROM RELATIONS WHERE USER_ID = ?1", nativeQuery = true)
    List<Relation> findByUserId(Long id);

}
