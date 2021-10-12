package com.kms.seft203.task;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task getById(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task id not found");
        }

        return taskRepository.getById(id);
    }

    public Task create(@NonNull SaveTaskRequest saveTaskRequest) {
        return taskRepository.saveAndFlush(Task.of(saveTaskRequest));
    }

    public Task update(Long id, SaveTaskRequest saveTaskRequest) {
        if (!taskRepository.existsById(id)) {
            return null;
        }

        var tobeUpdated = taskRepository.getById(id);
        if (ObjectUtils.isNotEmpty(tobeUpdated)) {
            BeanUtils.copyProperties(saveTaskRequest, tobeUpdated);
            taskRepository.saveAndFlush(tobeUpdated);
        }

        return tobeUpdated;
    }

    @NonNull
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
