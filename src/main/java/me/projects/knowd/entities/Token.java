package me.projects.knowd.entities;

import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonCreator;

@Entity
class Token {
    String tokenString;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Token(String tokenString) {
        this.tokenString = tokenString;
    }
}
