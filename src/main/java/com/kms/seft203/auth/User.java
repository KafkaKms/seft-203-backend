package com.kms.seft203.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kms.seft203.dashboard.Dashboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 20)
    private Long id;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 50)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private String fullName;

    @OneToOne(mappedBy = "user")
    private Dashboard dashboard;

    public static User of(RegisterRequest registerRequest) {
        return new User(
                0L,
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getFullName(),
                null
        );
    }
}
