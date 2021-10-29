package com.kms.seft203.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshRequest {
    @NotBlank
    private String refreshToken;
}
