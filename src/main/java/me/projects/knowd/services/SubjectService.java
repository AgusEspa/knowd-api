package me.projects.knowd.services;

import me.projects.knowd.dtos.requests.SubjectRequest;
import me.projects.knowd.dtos.responses.RelationResponse;
import me.projects.knowd.dtos.responses.SubjectResponse;
import me.projects.knowd.dtos.responses.TopicResponse;
import me.projects.knowd.entities.Relation;
import me.projects.knowd.entities.Subject;
import me.projects.knowd.entities.Topic;
import me.projects.knowd.entities.UserEntity;
import me.projects.knowd.exceptions.SubjectNotFoundException;
import me.projects.knowd.exceptions.UserEntityNotFoundException;
import me.projects.knowd.exceptions.UserNotAuthorizedException;
import me.projects.knowd.repositories.RelationRepository;
import me.projects.knowd.repositories.SubjectRepository;
import me.projects.knowd.repositories.TopicRepository;
import me.projects.knowd.repositories.UserEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    private final UserEntityRepository userEntityRepository;

    private final RelationRepository relationRepository;

    private final TopicRepository topicRepository;

    Logger logger = LoggerFactory.getLogger(SubjectService.class);


    @Autowired
    public SubjectService(SubjectRepository subjectRepository, UserEntityRepository userEntityRepository, RelationRepository relationRepository, TopicRepository topicRepository) {
        this.subjectRepository = subjectRepository;
        this.userEntityRepository = userEntityRepository;
        this.relationRepository = relationRepository;
        this.topicRepository = topicRepository;
    }

    public List<SubjectResponse> fetchSubjects() {

        String username = getUsername();

        UserEntity user = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));


        List<Subject> fetchedSubject = subjectRepository.findByUserId(user.getId());

        List<Relation> fetchedRelations = relationRepository.findByUserId(user.getId());

        List<Topic> fetchedTopics = topicRepository.findByUserId(user.getId());

        List<SubjectResponse> subjectResponseList = fetchedSubject.stream()
                .map(subject -> new SubjectResponse(
                        subject.getId(),
                        subject.getTitle(),
                        subject.getField(),
                        subject.getArea(),
                        subject.getRelevance(),
                        subject.getProgress(),
                        subject.getStatus(),
                        subject.getNeedsAttention(),
                        subject.getDueDate(),
                        fetchedRelations.stream()
                                .filter(relations -> relations.getSubject().getId().equals(subject.getId()))
                                .map(relation -> new RelationResponse(relation.getId(), relation.getTitle()))
                                .collect(Collectors.toList()),
                        fetchedTopics.stream()
                                .filter(topics -> topics.getSubject().getId().equals(subject.getId()))
                                .map(topic -> new TopicResponse(topic.getId(), topic.getTitle(), topic.getIsDone()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());

        return subjectResponseList;
    }

    public SubjectResponse newSubject(SubjectRequest subjectRequest) {

        String username = getUsername();

        UserEntity user = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));

        Subject newSubject = new Subject(
                subjectRequest.getTitle(),
                subjectRequest.getField(),
                subjectRequest.getArea(),
                subjectRequest.getRelevance(),
                subjectRequest.getProgress(),
                subjectRequest.getStatus(),
                subjectRequest.getNeedsAttention(),
                subjectRequest.getDueDate(),
                user);
        subjectRepository.save(newSubject);

        return new SubjectResponse(
                newSubject.getId(),
                newSubject.getTitle(),
                newSubject.getField(),
                newSubject.getArea(),
                newSubject.getRelevance(),
                newSubject.getProgress(),
                newSubject.getStatus(),
                newSubject.getNeedsAttention(),
                newSubject.getDueDate(),
                new ArrayList<RelationResponse>(),
                new ArrayList<TopicResponse>());

    }

    public SubjectResponse updateSubject(Long id, SubjectRequest editedSubject) {

        Subject fetchedSubject = validateUserAndFetchSubject(id);

        fetchedSubject.setTitle(editedSubject.getTitle());
        fetchedSubject.setField(editedSubject.getField());
        fetchedSubject.setArea(editedSubject.getArea());
        fetchedSubject.setRelevance(editedSubject.getRelevance());
        fetchedSubject.setProgress(editedSubject.getProgress());
        fetchedSubject.setStatus(editedSubject.getStatus());
        fetchedSubject.setNeedsAttention(editedSubject.getNeedsAttention());
        fetchedSubject.setDueDate(editedSubject.getDueDate());
        fetchedSubject.setUser(fetchedSubject.getUser());

        subjectRepository.save(fetchedSubject);

        return new SubjectResponse(
                fetchedSubject.getId(),
                fetchedSubject.getTitle(),
                fetchedSubject.getField(),
                fetchedSubject.getArea(),
                fetchedSubject.getRelevance(),
                fetchedSubject.getProgress(),
                fetchedSubject.getStatus(),
                fetchedSubject.getNeedsAttention(),
                fetchedSubject.getDueDate(),
                fetchedSubject.getRelations().stream()
                        .map(relation -> new RelationResponse(relation.getId(), relation.getTitle()))
                        .collect(Collectors.toList()),
                fetchedSubject.getTopics().stream()
                        .map(topic -> new TopicResponse(topic.getId(), topic.getTitle(), topic.getIsDone()))
                        .collect(Collectors.toList()));
    }

    public void removeSubject(Long id) {
        validateUserAndFetchSubject(id);
        subjectRepository.deleteById(id);
    }

    protected Subject validateUserAndFetchSubject(Long id) {

        String username = getUsername();

        Subject fetchedSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));

        if (!fetchedSubject.getUser().getEmailAddress().equals(username)) throw new UserNotAuthorizedException(username);
        else return fetchedSubject;
    }

    protected String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
