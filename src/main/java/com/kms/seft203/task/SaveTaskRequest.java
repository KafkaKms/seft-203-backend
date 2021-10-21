package com.kms.seft203.task;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class SaveTaskRequest {
    @NotBlank(message = "Task must not empty")
    protected String task;
    @NotNull(message = "userId must not empty")
    protected Long userId;
}
