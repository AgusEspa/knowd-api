package me.projects.knowd.services;

import me.projects.knowd.dtos.requests.AreaRequest;
import me.projects.knowd.dtos.responses.AreaResponse;
import me.projects.knowd.entities.*;
import me.projects.knowd.exceptions.AreaNotFoundException;
import me.projects.knowd.exceptions.FieldNotFoundException;
import me.projects.knowd.exceptions.UserEntityNotFoundException;
import me.projects.knowd.exceptions.UserNotAuthorizedException;
import me.projects.knowd.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    private final FieldRepository fieldRepository;

    private final UserEntityRepository userEntityRepository;

    Logger logger = LoggerFactory.getLogger(AreaService.class);

    @Autowired
    public AreaService(AreaRepository areaRepository, FieldRepository fieldRepository, UserEntityRepository userEntityRepository) {
        this.areaRepository = areaRepository;
        this.fieldRepository = fieldRepository;
        this.userEntityRepository = userEntityRepository;
    }

    public AreaResponse newArea(Long fieldId, AreaRequest areaRequest) {
        String username = getUsername();
        UserEntity user = userEntityRepository.findByEmailAddress(username)
                .orElseThrow(() -> new UserEntityNotFoundException(username));

        Field field = fieldRepository.findById(fieldId).orElseThrow(() -> new FieldNotFoundException(fieldId));

        Area newArea = new Area(
                areaRequest.getTitle(),
                field,
                user);
        areaRepository.save(newArea);

        return new AreaResponse(newArea.getId(), newArea.getTitle(), newArea.getField().getId());
    }

    public AreaResponse updateArea(Long id, AreaRequest editedArea) {

        Area fetchedArea = validateUserAndFetchArea(id);

        fetchedArea.setTitle(editedArea.getTitle());
        fetchedArea.setField(fetchedArea.getField());
        fetchedArea.setUser(fetchedArea.getUser());
        areaRepository.save(fetchedArea);

        return new AreaResponse(fetchedArea.getId(), fetchedArea.getTitle(), fetchedArea.getField().getId());
    }

    public void removeArea(Long id) {
        validateUserAndFetchArea(id);
        areaRepository.deleteById(id);

    }

     protected Area validateUserAndFetchArea(Long id) {

        String username = getUsername();

        Area fetchedArea = areaRepository.findById(id)
                .orElseThrow(() -> new AreaNotFoundException(id));

        if (!fetchedArea.getUser().getEmailAddress().equals(username)) throw new UserNotAuthorizedException(username);
        else return fetchedArea;
    }

    protected String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
