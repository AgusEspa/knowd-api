package me.projects.knowd.controllers;

import me.projects.knowd.dtos.requests.FieldRequest;
import me.projects.knowd.dtos.responses.FieldResponse;
import me.projects.knowd.services.FieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/fields")
public class FieldController {

    private final FieldService fieldService;
    Logger logger = LoggerFactory.getLogger(FieldService.class);

    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping
    ResponseEntity<List> getFields() {
        return ResponseEntity.ok(fieldService.fetchFields());
    }

    @PostMapping
    ResponseEntity<FieldResponse> createField(@Valid @RequestBody FieldRequest fieldRequest) {
        return ResponseEntity.ok(fieldService.newField(fieldRequest));
    }

    @PutMapping("/{id}")
    ResponseEntity<FieldResponse> editField(@PathVariable Long id, @RequestBody FieldRequest editedField) {
        return ResponseEntity.ok(fieldService.updateField(id, editedField));
    }

    @DeleteMapping("/{id}")
    void deleteField(@PathVariable Long id) {
        fieldService.removeField(id);
    }
}
