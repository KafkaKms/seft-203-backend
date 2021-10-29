package com.kms.seft203.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJwtRepository extends JpaRepository<UserJwt, Long> {
    Optional<UserJwt> findByUserId(Long userId);

    @Query("select u from UserJwt u where u.refreshToken = :refreshToken")
    Optional<UserJwt> findByRefreshToken(String refreshToken);
}
