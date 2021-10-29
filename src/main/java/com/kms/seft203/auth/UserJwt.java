package com.kms.seft203.auth;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class UserJwt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isValid;
}
