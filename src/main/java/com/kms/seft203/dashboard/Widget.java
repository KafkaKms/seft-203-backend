package com.kms.seft203.dashboard;

import lombok.Data;

import java.util.Map;

@Data
public class Widget {
    private String title;
    private String widgetType;
    private Integer minWidth;
    private Integer minHeight;
    private Map<String, Object> configs;
}
