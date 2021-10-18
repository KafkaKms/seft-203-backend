package com.kms.seft203.dashboard;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class SaveDashboardRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String layoutType;

    private Set<Widget> widgets;
}
