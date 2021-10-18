package com.kms.seft203.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kms.seft203.dashboard.Dashboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Dashboard> dashboards;

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
