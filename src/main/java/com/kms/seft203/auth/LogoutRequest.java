package com.kms.seft203.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LogoutRequest {
    @NotBlank
    private String token;
    private String userId;
}
