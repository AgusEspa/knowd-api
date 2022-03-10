package me.projects.knowd.repositories;

import me.projects.knowd.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {

    @Query(value = "SELECT * FROM FIELDS WHERE USER_ID = ?1", nativeQuery = true)
    List<Field> findByUserId(Long id);

}
