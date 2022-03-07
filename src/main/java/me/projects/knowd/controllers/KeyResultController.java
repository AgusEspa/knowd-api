package me.projects.knowd.controllers;

import me.projects.knowd.dtos.requests.KeyResultRequest;
import me.projects.knowd.dtos.responses.KeyResultResponse;

import me.projects.knowd.services.KeyResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/objectives")
public class KeyResultController {

    private final KeyResultService keyResultService;

    @Autowired
    public KeyResultController(KeyResultService taskService) {
        this.keyResultService = taskService;
    }

    @GetMapping("/{objectiveId}/keyresults")
    ResponseEntity<List> getKeyResultsPerObjective(@PathVariable Long objectiveId) {
        return ResponseEntity.ok(keyResultService.fetchKeyResultsPerGoal(objectiveId));
    }

    @PostMapping("/{objectiveId}/keyresults")
    ResponseEntity<KeyResultResponse> createKeyResult(@PathVariable Long objectiveId, @Valid @RequestBody KeyResultRequest keyResultRequest) {
        KeyResultResponse keyResult = keyResultService.newKeyResult(objectiveId, keyResultRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(keyResult);
    }

    @PutMapping("/{objectiveId}/keyresults/{id}")
    ResponseEntity<KeyResultResponse> editKeyResult(@PathVariable Long objectiveId, @PathVariable Long id, @RequestBody KeyResultRequest editedKeyResult) {
        return ResponseEntity.ok(keyResultService.updateKeyResult(id, editedKeyResult));
    }

    @DeleteMapping("/keyresults/{id}")
    void deleteTask(@PathVariable Long id) {
        keyResultService.removeKeyResult(id);
    }

}
