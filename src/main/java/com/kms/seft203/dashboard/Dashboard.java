package com.kms.seft203.dashboard;

import lombok.Data;

import java.util.List;

@Data
public class Dashboard {
    private String id;
    private String userId;
    private String title;
    private String layoutType;
    private List<Widget> widgets;
}
