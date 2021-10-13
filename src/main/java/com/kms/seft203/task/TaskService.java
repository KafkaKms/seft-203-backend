package com.kms.seft203.task;

import com.kms.seft203.exceptions.DataNotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

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
        var task = taskRepository.findById(id).orElse(null);

        if (ObjectUtils.isNotEmpty(task)) {
            return task;
        } else {
            throw new DataNotFoundException();
        }
    }

    public Task create(@NonNull SaveTaskRequest saveTaskRequest) {
        return taskRepository.saveAndFlush(Task.of(saveTaskRequest));
    }

    public Task update(Long id, SaveTaskRequest saveTaskRequest) {
        var tobeUpdated = taskRepository.findById(id).orElse(null);

        if (ObjectUtils.isNotEmpty(tobeUpdated)) {
            BeanUtils.copyProperties(saveTaskRequest, tobeUpdated);
            taskRepository.saveAndFlush(tobeUpdated);
        } else {
            throw new DataNotFoundException();
        }

        return tobeUpdated;
    }

    @NonNull
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
