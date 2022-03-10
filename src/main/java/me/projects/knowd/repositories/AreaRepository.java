package me.projects.knowd.repositories;

import me.projects.knowd.entities.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {

    @Query(value = "SELECT * FROM AREAS WHERE FIELD_ID = ?1", nativeQuery = true)
    List<Area> findAllPerField(Long fieldId);

}
