package com.kms.seft203.dashboard;

import com.kms.seft203.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
@RestController
@RequestMapping("/dashboards")
public class DashboardApi {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<List<Dashboard>> getAll(Authentication authentication) {
        var user = User.of((org.springframework.security.core.userdetails.User) authentication.getPrincipal());
        return ResponseEntity.ok(dashboardService.getAllByUserId(user.getId()));
    }

    @PostMapping
    public ResponseEntity<Dashboard> create(@RequestBody @Valid SaveDashboardRequest saveDashboardRequest, Authentication authentication) {
        var user = (User) User.of((org.springframework.security.core.userdetails.User) authentication.getPrincipal());
        return ResponseEntity.ok(dashboardService.create(saveDashboardRequest, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dashboard> save(@PathVariable Long id,
                                          @RequestBody @Valid SaveDashboardRequest saveDashboardRequest,
                                          Authentication authentication) {
        var user = User.of((org.springframework.security.core.userdetails.User) authentication.getPrincipal());
        return ResponseEntity.ok(dashboardService.update(id, saveDashboardRequest, user));
    }
}
