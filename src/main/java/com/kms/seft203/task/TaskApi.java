package com.kms.seft203.task;

import com.kms.seft203.auth.User;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        if (ObjectUtils.isNotEmpty(id)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(taskService.getById(id));
        }

        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody SaveTaskRequest saveTaskRequest,
                                       Authentication authentication) {
        var user = User.of((org.springframework.security.core.userdetails.User) authentication.getPrincipal());

        if (ObjectUtils.isNotEmpty(saveTaskRequest)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(taskService.create(saveTaskRequest, user));
        }

        return ResponseEntity.badRequest().body(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> update(@PathVariable Long id, @Valid @RequestBody SaveTaskRequest saveTaskRequest,
                                       Authentication authentication) {
        var user = User.of((org.springframework.security.core.userdetails.User) authentication.getPrincipal());

        if (ObjectUtils.isNotEmpty(id)) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(taskService.update(id, saveTaskRequest, user));
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
