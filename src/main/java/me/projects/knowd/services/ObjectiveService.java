package me.projects.knowd.services;

import me.projects.knowd.dtos.requests.ObjectiveRequest;
import me.projects.knowd.dtos.responses.ObjectiveResponse;
import me.projects.knowd.entities.Objective;
import me.projects.knowd.entities.UserEntity;
import me.projects.knowd.exceptions.ObjectiveNotFoundException;
import me.projects.knowd.exceptions.UserEntityNotFoundException;
import me.projects.knowd.exceptions.UserNotAuthorizedException;
import me.projects.knowd.repositories.ObjectiveRepository;
import me.projects.knowd.repositories.UserEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObjectiveService {

    private final ObjectiveRepository objectiveRepository;

    private final UserEntityRepository userEntityRepository;

    Logger logger = LoggerFactory.getLogger(ObjectiveService.class);


    @Autowired
    public ObjectiveService(ObjectiveRepository objectiveRepository, UserEntityRepository userEntityRepository) {
        this.objectiveRepository = objectiveRepository;
        this.userEntityRepository = userEntityRepository;
    }

    public List<ObjectiveResponse> fetchObjectives() {

        String username = getUsername();

        UserEntity user = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));

        List<ObjectiveResponse> objectiveResponseList = objectiveRepository.findByUserId(user.getId()).stream()
                .map(objective -> new ObjectiveResponse(objective.getId(), objective.getTitle(), objective.getImportance()))
                .collect(Collectors.toList());

        return objectiveResponseList;
    }

    public ObjectiveResponse newObjective(ObjectiveRequest objectiveRequest) {

        String username = getUsername();

        UserEntity user = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));

        Objective newObjective = new Objective(objectiveRequest.getTitle(), objectiveRequest.getImportance(), user);
        objectiveRepository.save(newObjective);

        ObjectiveResponse objectiveResponse = new ObjectiveResponse(newObjective.getId(), newObjective.getTitle(), newObjective.getImportance());
        return objectiveResponse;
    }

    public ObjectiveResponse updateObjective(Long id, ObjectiveRequest editedObjective) {

        Objective fetchedObjective = validateUserAndFetchObjective(id);

        fetchedObjective.setTitle(editedObjective.getTitle());
        fetchedObjective.setImportance(editedObjective.getImportance());
        objectiveRepository.save(fetchedObjective);

        ObjectiveResponse objectiveResponse = new ObjectiveResponse(fetchedObjective.getId(),fetchedObjective.getTitle(), fetchedObjective.getImportance());
        return objectiveResponse;
    }

    public void removeObjective(Long id) {

        validateUserAndFetchObjective(id);
        objectiveRepository.deleteById(id);
    }

    protected Objective validateUserAndFetchObjective(Long id) {

        String username = getUsername();

        Objective fetchedObjective = objectiveRepository.findById(id)
                .orElseThrow(() -> new ObjectiveNotFoundException(id));

        if (!fetchedObjective.getUser().getEmailAddress().equals(username)) throw new UserNotAuthorizedException(username);
        else return fetchedObjective;
    }

    protected String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
