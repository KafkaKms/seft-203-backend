package com.kms.seft203.contact;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SaveContactRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String title;

    private String department;

    private String project;

    private String avatar;

    @NotBlank
    private Integer employeeId;
}
