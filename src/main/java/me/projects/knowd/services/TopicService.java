package me.projects.knowd.services;

import me.projects.knowd.dtos.requests.TopicRequest;
import me.projects.knowd.dtos.responses.TopicResponse;
import me.projects.knowd.entities.Subject;
import me.projects.knowd.entities.Topic;
import me.projects.knowd.entities.UserEntity;
import me.projects.knowd.exceptions.TopicNotFoundException;
import me.projects.knowd.exceptions.UserEntityNotFoundException;
import me.projects.knowd.exceptions.UserNotAuthorizedException;
import me.projects.knowd.repositories.SubjectRepository;
import me.projects.knowd.repositories.TopicRepository;
import me.projects.knowd.repositories.UserEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    private final UserEntityRepository userEntityRepository;

    private final SubjectRepository subjectRepository;

    Logger logger = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    public TopicService(TopicRepository repository, UserEntityRepository userEntityRepository, SubjectRepository subjectRepository) {
        this.topicRepository = repository;
        this.userEntityRepository = userEntityRepository;
        this.subjectRepository = subjectRepository;
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

    public TopicResponse partiallyUpdateTopic(Long id, Map<String, Object> changes) {

        Topic fetchedTopic = validateUserAndFetchTopic(id);

        changes.forEach(
                (change, value) -> {
                    switch (change){
                        case "title": fetchedTopic.setTitle((String) value); break;
                        case "isDone": fetchedTopic.setIsDone((Boolean) value); break;
                    }
                }
        );
        topicRepository.save(fetchedTopic);

        return new TopicResponse(fetchedTopic.getId(), fetchedTopic.getTitle(), fetchedTopic.getIsDone());
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
