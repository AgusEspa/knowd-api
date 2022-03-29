package me.projects.knowd.controllers;

import me.projects.knowd.dtos.requests.RelationRequest;
import me.projects.knowd.dtos.responses.RelationResponse;
import me.projects.knowd.services.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/subjects")
public class RelationController {

    private final RelationService relationService;

    @Autowired

    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }


    @PostMapping("/{subjectId}/relations")
    ResponseEntity<RelationResponse> createRelation(@PathVariable Long subjectId, @Valid @RequestBody RelationRequest relationRequest) {
        return ResponseEntity.ok(relationService.newRelation(subjectId, relationRequest));

    }

    @DeleteMapping("/relations/{id}")
    void deleteRelation(@PathVariable Long id) {
        relationService.removeRelation(id);
    }
}
