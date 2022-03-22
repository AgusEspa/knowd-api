package me.projects.knowd.controllers;


import me.projects.knowd.dtos.requests.TopicRequest;
import me.projects.knowd.dtos.responses.TopicResponse;
import me.projects.knowd.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/subjects")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/{subjectId}/topics")
    ResponseEntity<TopicResponse> createTopic(@PathVariable Long subjectId, @Valid @RequestBody TopicRequest topicRequest) {
        return ResponseEntity.ok(topicService.newTopic(subjectId, topicRequest));
    }

    @PutMapping("/topics/{id}")
    ResponseEntity<TopicResponse> editTopic(@PathVariable Long id, @RequestBody TopicRequest editedTopic) {
        return ResponseEntity.ok(topicService.updateTopic(id, editedTopic));
    }

    @DeleteMapping("/topics/{id}")
    void deleteTopic(@PathVariable Long id) {
        topicService.removeTopic(id);
    }

}
