package com.kms.seft203.auth;

import com.kms.seft203.dashboard.Dashboard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(length = 20)
    private Long id;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 50)
    private String email;

    @Column(length = 20, nullable = false)
    private String password;

    private String fullName;

    @OneToOne(mappedBy = "user")
    private Dashboard dashboard;
}
