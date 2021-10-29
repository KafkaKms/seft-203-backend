package com.kms.seft203.auth;

import com.kms.seft203.exceptions.DuplicateException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserJwtRepository userJwtRepository;

    public User register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new DuplicateException("Username has been already existed");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new DuplicateException("Email has been already existed");
        }

        var user = User.of(registerRequest);

        var encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public LoginResponse login(String username, String password) {
        var user = userRepository.findByUsername(username).orElse(null);

        if (ObjectUtils.isEmpty(user)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        var encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(password, user.getPassword())) { //NOSONAR
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        var token = jwtService.generateJwtToken(user);

        var userJwt = userJwtRepository.findByUserId(user.getId());

        var refreshToken = userJwt.isEmpty() ? jwtService.generateRefreshToken() : userJwt.get().getRefreshToken();

        if (userJwt.isEmpty()) {
            userJwtRepository.save(new UserJwt(0L, refreshToken, user, true));
        }

        return new LoginResponse(
                token,
                refreshToken
        );
    }

    public LoginResponse refresh(RefreshRequest refreshRequest) {
        var userJwt = userJwtRepository.findByRefreshToken(refreshRequest.getRefreshToken());

        if (userJwt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Can't find refresh token");
        }

        return new LoginResponse(
                jwtService.generateJwtToken(userJwt.get().getUser()),
                refreshRequest.getRefreshToken()
        );
    }

    public void logout(Long userId) {
        var userJwt = userJwtRepository.findByUserId(userId).orElse(null);
        assert userJwt != null;
        userJwt.setValid(false);

        userJwtRepository.delete(userJwt);
    }
}
