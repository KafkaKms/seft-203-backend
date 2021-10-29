package com.kms.seft203.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unused", "SpringJavaAutowiredFieldsWarningInspection"})
@RestController
@RequestMapping("/auth")
public class AuthApi {

    @Autowired
    private UserService userService;

    private static final Map<String, User> DATA = new HashMap<>();

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(userService.login(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        userService.logout(user.getId());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@RequestBody @Valid RefreshRequest refreshRequest) {
        return ResponseEntity.ok(userService.refresh(refreshRequest));
    }
}
