package me.projects.SelfOKRs.repositories;

import me.projects.SelfOKRs.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailAddress(String emailAddress);

}
