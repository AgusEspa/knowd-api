package me.projects.knowd.controllers;

import me.projects.knowd.dtos.requests.ObjectiveRequest;
import me.projects.knowd.dtos.responses.ObjectiveResponse;
import me.projects.knowd.services.ObjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/objectives")
public class ObjectiveController {

    private final ObjectiveService objectiveService;

    @Autowired
    public ObjectiveController(ObjectiveService objectiveService) {
        this.objectiveService = objectiveService;
    }

    @GetMapping
    ResponseEntity<List> getObjectives() {
        return ResponseEntity.ok(objectiveService.fetchObjectives());
    }

    @PostMapping
    ResponseEntity<ObjectiveResponse> createObjective(@Valid @RequestBody ObjectiveRequest objectiveRequest) {
        ObjectiveResponse objective = objectiveService.newObjective(objectiveRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(objective);
    }

    @PutMapping("/{id}")
    ResponseEntity<ObjectiveResponse> editObjective(@PathVariable Long id, @RequestBody ObjectiveRequest editedObjective) {
        return ResponseEntity.ok(objectiveService.updateObjective(id, editedObjective));
    }

    @DeleteMapping("/{id}")
    void deleteObjective(@PathVariable Long id) {
        objectiveService.removeObjective(id);
    }
}
