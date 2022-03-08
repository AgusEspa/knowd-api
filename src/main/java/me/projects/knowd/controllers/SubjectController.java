package me.projects.knowd.controllers;

import me.projects.knowd.dtos.requests.SubjectRequest;
import me.projects.knowd.dtos.responses.SubjectResponse;
import me.projects.knowd.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    ResponseEntity<List> getSubjects() {
        return ResponseEntity.ok(subjectService.fetchSubjects());
    }

    @PostMapping
    ResponseEntity<SubjectResponse> createSubject(@Valid @RequestBody SubjectRequest subjectRequest) {
        return ResponseEntity.ok(subjectService.newSubject(subjectRequest));
    }

    @PutMapping("/{id}")
    void editSubject(@PathVariable Long id, @RequestBody SubjectRequest editedSubject) {
        subjectService.updateSubject(id, editedSubject);
    }

    @DeleteMapping("/{id}")
    void deleteSubject(@PathVariable Long id) {
        subjectService.removeSubject(id);
    }
}
