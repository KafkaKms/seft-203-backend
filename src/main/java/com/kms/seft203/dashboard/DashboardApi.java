package com.kms.seft203.dashboard;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dashboards")
public class DashboardApi {

    private static final Map<String, Dashboard> DATA = new HashMap<>();

    @GetMapping
    public List<Dashboard> getAll() {
        // TODO: get all dashboard of a logged in user
        return new ArrayList<>(DATA.values());
    }

    @PutMapping("/{id}")
    public Dashboard save(@PathVariable String id, @RequestBody SaveDashboardRequest request) {
        DATA.put(id, request);
        return request;
    }
}
