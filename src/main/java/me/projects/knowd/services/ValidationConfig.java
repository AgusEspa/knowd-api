package me.projects.knowd.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

@Configuration
public class ValidationConfig {

    @Bean
    Validator validator() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        return validator;
    }
}
