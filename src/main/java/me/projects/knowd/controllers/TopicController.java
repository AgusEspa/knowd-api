package me.projects.knowd.controllers;


import me.projects.knowd.dtos.requests.TopicRequest;
import me.projects.knowd.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/subjects")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService taskService) {
        this.topicService = taskService;
    }


    @PostMapping("/{subjectId}/topics")
    void createKeyResult(@PathVariable Long subjectId, @Valid @RequestBody TopicRequest topicRequest) {
        topicService.newTopic(subjectId, topicRequest);

    }

    @PutMapping("/{subjectId}/topics/{id}")
    void editTopic(@PathVariable Long subjectId, @PathVariable Long id, @RequestBody TopicRequest editedTopic) {
        topicService.updateTopic(id, editedTopic);
    }

    @DeleteMapping("/topics/{id}")
    void deleteTopic(@PathVariable Long id) {
        topicService.removeTopic(id);
    }

}
