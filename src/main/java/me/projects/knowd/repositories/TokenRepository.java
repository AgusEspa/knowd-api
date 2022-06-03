package me.projects.knowd.repositories;

import me.projects.knowd.entities.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<String, Long> {
    @Query(value = "SELECT 1 FROM revoqued_tokens WHERE token_string = :token", nativeQuery = true)
    Optional<Token> findByString(String token);
}
