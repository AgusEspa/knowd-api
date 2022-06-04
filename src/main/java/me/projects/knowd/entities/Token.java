package me.projects.knowd.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Objects;

@Entity
@Table(name = "revoqued_tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @NotBlank
    @Column(name = "token_string")
    String tokenString;

    public Token() {
    };

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Token(String tokenString) {
        this.tokenString = tokenString;
    }

    public String getTokenString() {
        return tokenString;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
            Token token = (Token) o;
        return Objects.equals(tokenString, token.tokenString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenString);
    }
}
