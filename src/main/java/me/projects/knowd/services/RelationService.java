package me.projects.knowd.services;

import me.projects.knowd.dtos.requests.RelationRequest;
import me.projects.knowd.dtos.responses.RelationResponse;
import me.projects.knowd.dtos.responses.SubjectResponse;
import me.projects.knowd.entities.Relation;
import me.projects.knowd.entities.Subject;
import me.projects.knowd.entities.UserEntity;
import me.projects.knowd.exceptions.RelationNotFoundException;
import me.projects.knowd.exceptions.UserEntityNotFoundException;
import me.projects.knowd.exceptions.UserNotAuthorizedException;
import me.projects.knowd.repositories.RelationRepository;
import me.projects.knowd.repositories.SubjectRepository;
import me.projects.knowd.repositories.UserEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class RelationService {

    private final RelationRepository relationRepository;

    private final UserEntityRepository userEntityRepository;

    private final SubjectRepository subjectRepository;

    Logger logger = LoggerFactory.getLogger(TopicService.class);

    @Autowired
    public RelationService(RelationRepository relationRepository, UserEntityRepository userEntityRepository, SubjectRepository subjectRepository) {
        this.relationRepository = relationRepository;
        this.userEntityRepository = userEntityRepository;
        this.subjectRepository = subjectRepository;
    }

    public void newRelation(Long subjectId, RelationRequest relationRequest) {
        String username = getUsername();
        UserEntity user = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));

        Subject subject = subjectRepository.getById(subjectId);

        Relation newRelation = new Relation(
                relationRequest.getTitle(),
                subject,
                user);
        relationRepository.save(newRelation);
    }

    public void updateRelation(Long id, RelationRequest editedRelation) {

        Relation fetchedRelation = validateUserAndFetchRelation(id);

        fetchedRelation.setTitle(editedRelation.getTitle());
        fetchedRelation.setSubject(fetchedRelation.getSubject());
        fetchedRelation.setUser(fetchedRelation.getUser());
        relationRepository.save(fetchedRelation);
    }

    public void removeRelation(Long id) {
        validateUserAndFetchRelation(id);
        relationRepository.deleteById(id);

    }

    protected Relation validateUserAndFetchRelation(Long id) {

        String username = getUsername();

        Relation fetchedRelation = relationRepository.findById(id)
                .orElseThrow(() -> new RelationNotFoundException(id));

        if (!fetchedRelation.getUser().getEmailAddress().equals(username)) throw new UserNotAuthorizedException(username);
        else return fetchedRelation;
    }

    protected String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
