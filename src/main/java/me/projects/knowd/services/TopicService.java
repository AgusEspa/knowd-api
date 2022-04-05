package me.projects.knowd.services;

import me.projects.knowd.controllers.TopicController;
import me.projects.knowd.dtos.requests.TopicRequest;
import me.projects.knowd.dtos.responses.TopicResponse;
import me.projects.knowd.entities.Subject;
import me.projects.knowd.entities.Topic;
import me.projects.knowd.entities.UserEntity;
import me.projects.knowd.exceptions.*;
import me.projects.knowd.repositories.SubjectRepository;
import me.projects.knowd.repositories.TopicRepository;
import me.projects.knowd.repositories.UserEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    private final UserEntityRepository userEntityRepository;

    private final SubjectRepository subjectRepository;

    private final Validator validator;

    Logger logger = LoggerFactory.getLogger(TopicService.class);

    public TopicService(TopicRepository repository, UserEntityRepository userEntityRepository, SubjectRepository subjectRepository, Validator validator) {
        this.topicRepository = repository;
        this.userEntityRepository = userEntityRepository;
        this.subjectRepository = subjectRepository;
        this.validator = validator;
    }

    public TopicResponse newTopic(Long subjectId, TopicRequest topicRequest) {
        String username = getUsername();
        UserEntity user = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));

        Subject subject = subjectRepository.getById(subjectId);

        Topic newTopic = new Topic(
                topicRequest.getTitle(),
                topicRequest.getIsDone(),
                subject,
                user);
        topicRepository.save(newTopic);

        return new TopicResponse(newTopic.getId(), newTopic.getTitle(), newTopic.getIsDone());
    }

    public TopicResponse updateTopic(Long id, TopicRequest editedTopic) {

        Topic fetchedTopic = validateUserAndFetchTopic(id);

        fetchedTopic.setTitle(editedTopic.getTitle());
        fetchedTopic.setIsDone(editedTopic.getIsDone());
        topicRepository.save(fetchedTopic);

        return new TopicResponse(fetchedTopic.getId(), fetchedTopic.getTitle(), fetchedTopic.getIsDone());
    }

    public TopicResponse partiallyUpdateTopic(Long id, Map<String, Object> changes) throws NoSuchMethodException, MethodArgumentNotValidException {

        Topic fetchedTopic = validateUserAndFetchTopic(id);

        TopicRequest editedTopic = new TopicRequest(fetchedTopic.getTitle(), fetchedTopic.getIsDone());

        changes.forEach(
                (change, value) -> {
                    switch (change) {
                        case "title" -> editedTopic.setTitle((String) value);
                        case "isDone" -> {
                                try {
                                    editedTopic.setIsDone((boolean) value);
                                } catch (RuntimeException e) {
                                    throw new CustomMethodArgumentNotValidException("Not a valid boolean value");
                                }
                        }
                    }
                }
        );

        Set<ConstraintViolation<TopicRequest>> violations = validator.validate(editedTopic);

        if (!violations.isEmpty()) {

            BeanPropertyBindingResult result = new BeanPropertyBindingResult(editedTopic, "editedTopic");

            violations.forEach(error -> {
                FieldError fieldError = new FieldError("topicRequest", error.getPropertyPath().toString(), error.getMessage());
                result.addError(fieldError);
            });

            Class[] carr = {Long.class, Map.class};

            Method method = TopicController.class.getMethod("editTopic", carr);

            throw new MethodArgumentNotValidException (
                    new MethodParameter(
                            method,
                            0),
                    result);
        }

        fetchedTopic.setTitle(editedTopic.getTitle());
        fetchedTopic.setIsDone(editedTopic.getIsDone());
        topicRepository.save(fetchedTopic);

        return new TopicResponse(
                        fetchedTopic.getId(),
                        fetchedTopic.getTitle(),
                        fetchedTopic.getIsDone()
        );
    }

    public void removeTopic(Long id) {
        validateUserAndFetchTopic(id);
        topicRepository.deleteById(id);
    }

     protected Topic validateUserAndFetchTopic(Long id) {

        String username = getUsername();

        Topic fetchedTopic = topicRepository.findById(id)
                .orElseThrow(() -> new TopicNotFoundException(id));

        if (!fetchedTopic.getUser().getEmailAddress().equals(username)) throw new UserNotAuthorizedException(username);
        else return fetchedTopic;
    }

    protected String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
