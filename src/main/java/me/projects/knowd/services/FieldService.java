package me.projects.knowd.services;

import me.projects.knowd.dtos.requests.FieldRequest;
import me.projects.knowd.dtos.responses.*;
import me.projects.knowd.entities.Field;
import me.projects.knowd.entities.UserEntity;
import me.projects.knowd.exceptions.FieldNotFoundException;
import me.projects.knowd.exceptions.UserEntityNotFoundException;
import me.projects.knowd.exceptions.UserNotAuthorizedException;
import me.projects.knowd.repositories.FieldRepository;
import me.projects.knowd.repositories.UserEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldService {

    private final FieldRepository fieldRepository;

    private final UserEntityRepository userEntityRepository;

    Logger logger = LoggerFactory.getLogger(FieldService.class);


    @Autowired
    public FieldService(FieldRepository fieldRepository, UserEntityRepository userEntityRepository) {
        this.fieldRepository = fieldRepository;
        this.userEntityRepository = userEntityRepository;
    }

    public List<FieldResponse> fetchFields() {

        String username = getUsername();

        UserEntity user = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));



        return fieldRepository.findByUserId(user.getId()).stream()
                .map(field -> new FieldResponse(
                        field.getId(),
                        field.getTitle(),
                        field.getAreas().stream()
                                .map(topic -> new AreaResponse(
                                        topic.getId(),
                                        topic.getTitle()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public FieldResponse newField(FieldRequest fieldRequest) {

        String username = getUsername();

        UserEntity user = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));

        Field newField = new Field(
                fieldRequest.getTitle(),
                user);
        fieldRepository.save(newField);

        return new FieldResponse(
                newField.getId(),
                newField.getTitle(),
                newField.getAreas().stream()
                        .map(topic -> new AreaResponse(
                                        topic.getId(),
                                        topic.getTitle()))
                        .collect(Collectors.toList()));
    }

    public FieldResponse updateField(Long id, FieldRequest editedField) {

        Field fetchedField = validateUserAndFetchField(id);

        fetchedField.setTitle(editedField.getTitle());
        fetchedField.setUser(fetchedField.getUser());

        fieldRepository.save(fetchedField);

        return new FieldResponse(
                fetchedField.getId(),
                fetchedField.getTitle(),
                fetchedField.getAreas().stream()
                        .map(topic -> new AreaResponse(
                                topic.getId(),
                                topic.getTitle()))
                        .collect(Collectors.toList()));
    }

    public void removeField(Long id) {
        validateUserAndFetchField(id);
        fieldRepository.deleteById(id);
    }

    protected Field validateUserAndFetchField(Long id) {

        String username = getUsername();

        Field fetchedField = fieldRepository.findById(id)
                .orElseThrow(() -> new FieldNotFoundException(id));

        if (!fetchedField.getUser().getEmailAddress().equals(username)) throw new UserNotAuthorizedException(username);
        else return fetchedField;
    }

    protected String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
