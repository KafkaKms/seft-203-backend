package com.kms.seft203.task;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskApi {

    final TaskService taskService;

    public TaskApi(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getOne(@PathVariable Long id) {
        if (ObjectUtils.isNotEmpty(id)) {
            return ResponseEntity.ok(taskService.getById(id));
        }

        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody SaveTaskRequest saveTaskRequest) {
        if (ObjectUtils.isNotEmpty(saveTaskRequest)) {
            return ResponseEntity.ok(taskService.create(saveTaskRequest));
        }

        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody SaveTaskRequest saveTaskRequest) {
        if (ObjectUtils.isNotEmpty(id)) {
            return ResponseEntity.ok(taskService.update(id, saveTaskRequest));
        }

        return ResponseEntity.badRequest().body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> delete(@PathVariable Long id) {
        if (ObjectUtils.isNotEmpty(id)) {
            taskService.delete(id);
        }

        return ResponseEntity.noContent().build();
    }
}
