package com.kms.seft203.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.username = :username")
    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.username = :email")
    Optional<User> findByEmail(String email);
}
