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
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }


    @PostMapping("/{subjectId}/topics")
    void createTopic(@PathVariable Long subjectId, @Valid @RequestBody TopicRequest topicRequest) {
        topicService.newTopic(subjectId, topicRequest);

    }

    @PutMapping("/topics/{id}")
    void editTopic(@PathVariable Long id, @RequestBody TopicRequest editedTopic) {
        topicService.updateTopic(id, editedTopic);
    }

    @DeleteMapping("/topics/{id}")
    void deleteTopic(@PathVariable Long id) {
        topicService.removeTopic(id);
    }

}
