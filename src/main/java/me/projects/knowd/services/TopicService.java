package me.projects.knowd.services;

import me.projects.knowd.dtos.requests.TopicRequest;
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

    public void newTopic(Long subjectId, TopicRequest topicRequest) {
        String username = getUsername();
        UserEntity user = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));

        Subject subject = subjectRepository.getById(subjectId);

        Topic newTopic = new Topic(
                topicRequest.getTitle(),
                topicRequest.getProgress(),
                topicRequest.getStatus(),
                subject,
                user);
        topicRepository.save(newTopic);
    }

    public void updateTopic(Long id, TopicRequest editedTopic) {

        Topic fetchedTopic = validateUserAndFetchTopic(id);

        fetchedTopic.setTitle(editedTopic.getTitle());
        fetchedTopic.setProgress(editedTopic.getProgress());
        fetchedTopic.setStatus(editedTopic.getStatus());
        fetchedTopic.setSubject(fetchedTopic.getSubject());
        fetchedTopic.setUser(fetchedTopic.getUser());
        topicRepository.save(fetchedTopic);
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
