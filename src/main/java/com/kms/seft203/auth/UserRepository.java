package com.kms.seft203.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select 1 from User u where u.username = :username")
    boolean existsByUsername(String username);

    @Query("select 1 from User u where u.email = :email")
    boolean existsByEmail(String email);

}
