package com.nicolas.simpletodo.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.nicolas.simpletodo.models.Task;
import com.nicolas.simpletodo.services.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findTaskById(@PathVariable Long id) {
        return ResponseEntity.ok().body(taskService.findById(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Task>> findAllTasksByUserId(@PathVariable Long id) {
        return ResponseEntity.ok().body(taskService.findAllByUserId(id));
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> createTask(@Valid @RequestBody Task task) {
        taskService.createTask(task);

        URI uri = UriComponentsBuilder
                .fromPath("/task/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> updateTask(@Valid @RequestBody Task task, @PathVariable Long id) {
        task.setId(id);
        taskService.updateTask(task);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}
