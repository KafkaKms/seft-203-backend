package com.kms.seft203.task;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskApi {

    private static final Map<String, Task> DATA = new HashMap<>();

    @GetMapping
    public List<Task> getAll() {
        return new ArrayList<>(DATA.values());
    }

    @GetMapping("/{id}")
    public Task getOne(@PathVariable String id) {
        return DATA.get(id);
    }

    @PostMapping
    public Task create(@RequestBody SaveTaskRequest request) {
        DATA.put(request.getId(), request);
        return request;
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable String id, @RequestBody SaveTaskRequest request) {
        DATA.put(id, request);
        return request;
    }

    @DeleteMapping("/{id}")
    public Task delete(@PathVariable String id) {
        return DATA.remove(id);
    }
}
