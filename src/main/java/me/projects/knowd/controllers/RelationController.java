package me.projects.knowd.controllers;

import me.projects.knowd.dtos.requests.RelationRequest;
import me.projects.knowd.services.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class RelationController {

    private final RelationService relationService;

    @Autowired

    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }


    @PostMapping("/{subjectId}/relations")
    void createRelation(@PathVariable Long subjectId, @Valid @RequestBody RelationRequest relationRequest) {
        relationService.newRelation(subjectId, relationRequest);

    }

    @PutMapping("/{subjectId}/relations/{id}")
    void editRelation(@PathVariable Long subjectId, @PathVariable Long id, @RequestBody RelationRequest editedRelation) {
        relationService.updateRelation(id, editedRelation);
    }

    @DeleteMapping("/relations/{id}")
    void deleteRelation(@PathVariable Long id) {
        relationService.removeRelation(id);
    }
}
