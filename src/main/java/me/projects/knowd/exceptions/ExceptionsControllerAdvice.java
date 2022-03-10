package me.projects.knowd.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionsControllerAdvice {

    @ExceptionHandler(UserEntityNotFoundException.class)
    ResponseEntity<String> handleUserNotFoundException(UserEntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(TopicNotFoundException.class)
    ResponseEntity<String> handleKeyResultNotFoundException(TopicNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RelationNotFoundException.class)
    ResponseEntity<String> handleRelationNotFoundException(RelationNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    ResponseEntity<String> handleSubjectNotFoundException(SubjectNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(FieldNotFoundException.class)
    ResponseEntity<String> handleFieldNotFoundException(FieldNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(AreaNotFoundException.class)
    ResponseEntity<String> handleAreaNotFoundException(AreaNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ex.getMessage());
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    ResponseEntity<String> handleUserNotAuthorizedException(UserNotAuthorizedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map> handleConstraintViolationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Map>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomMethodArgumentNotValidException.class)
    ResponseEntity<String> handleCustomMethodArgumentNotValidException(CustomMethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(WrongPasswordException.class)
    ResponseEntity<String> handleWrongPasswordException (WrongPasswordException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }

    @ExceptionHandler(InvalidTokenException.class)
    ResponseEntity<String> handleInvalidTokenException (InvalidTokenException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }
}
