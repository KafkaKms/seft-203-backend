package com.kms.seft203.task;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SaveTaskRequest {
    @NotBlank(message = "Task must not empty")
    protected String task;
    @NotBlank(message = "userId must not empty")
    protected String userId;
}
