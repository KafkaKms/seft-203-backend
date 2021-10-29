package com.kms.seft203;

import com.kms.seft203.auth.AuthApi;
import com.kms.seft203.auth.JwtService;
import com.kms.seft203.auth.UserJwtRepository;
import com.kms.seft203.auth.UserRepository;
import com.kms.seft203.auth.UserService;
import com.kms.seft203.task.TaskApi;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = AuthApi.class)
class AuthApiTest {
    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    JwtService jwtService;

    @MockBean
    UserJwtRepository userJwtRepository;

    @Test
    void register() {
    }

    @Test
    void login() {
    }

    @Test
    void logout() {
    }

    @Test
    void refresh() {
    }
}