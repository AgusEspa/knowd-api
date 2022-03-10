package me.projects.knowd.controllers;


import me.projects.knowd.dtos.requests.AreaRequest;
import me.projects.knowd.dtos.responses.AreaResponse;
import me.projects.knowd.services.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/fields")
public class AreaController {

    private final AreaService areaService;

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }


    @PostMapping("/{fieldId}/areas")
    ResponseEntity<AreaResponse> createArea(@PathVariable Long fieldId, @Valid @RequestBody AreaRequest areaRequest) {
        return ResponseEntity.ok(areaService.newArea(fieldId, areaRequest));

    }

    @PutMapping("/areas/{id}")
    ResponseEntity<AreaResponse> editArea(@PathVariable Long id, @RequestBody AreaRequest editedArea) {
        return ResponseEntity.ok(areaService.updateArea(id, editedArea));
    }

    @DeleteMapping("/areas/{id}")
    void deleteArea(@PathVariable Long id) {
        areaService.removeArea(id);
    }

}
