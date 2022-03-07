package me.projects.knowd.repositories;

import me.projects.knowd.entities.Objective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ObjectiveRepository extends JpaRepository<Objective, Long> {

    @Query(value = "SELECT * FROM OBJECTIVES WHERE USER_ID = ?1", nativeQuery = true)
    List<Objective> findByUserId(Long id);

}
