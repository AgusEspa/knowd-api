package me.projects.knowd.services;

import me.projects.knowd.dtos.requests.SubjectRequest;
import me.projects.knowd.dtos.responses.SubjectResponse;
import me.projects.knowd.dtos.responses.TopicResponse;
import me.projects.knowd.entities.Subject;
import me.projects.knowd.entities.UserEntity;
import me.projects.knowd.exceptions.SubjectNotFoundException;
import me.projects.knowd.exceptions.UserEntityNotFoundException;
import me.projects.knowd.exceptions.UserNotAuthorizedException;
import me.projects.knowd.repositories.SubjectRepository;
import me.projects.knowd.repositories.UserEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    private final UserEntityRepository userEntityRepository;

    Logger logger = LoggerFactory.getLogger(SubjectService.class);


    @Autowired
    public SubjectService(SubjectRepository subjectRepository, UserEntityRepository userEntityRepository) {
        this.subjectRepository = subjectRepository;
        this.userEntityRepository = userEntityRepository;
    }

    public List<SubjectResponse> fetchSubjects() {

        String username = getUsername();

        UserEntity user = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));

        List<SubjectResponse> subjectResponseList = subjectRepository.findByUserId(user.getId()).stream()
                .map(subject -> new SubjectResponse(
                        subject.getId(),
                        subject.getTitle(),
                        subject.getField(),
                        subject.getArea(),
                        subject.getTopics().stream()
                                .map(topic -> ),
                        subject.getRelations(),
                        subject.getRelevance(),
                        subject.getProgress(),
                        subject.getStatus(),
                        subject.getNeedsAttention(),
                        subject.getDueDate()))
                .collect(Collectors.toList());

        return subjectResponseList;
    }

    public void newSubject(SubjectRequest subjectRequest) {

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
    }

    public void updateSubject(Long id, SubjectRequest editedSubject) {

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
