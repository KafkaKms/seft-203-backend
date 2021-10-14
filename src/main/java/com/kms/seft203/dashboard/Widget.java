package com.kms.seft203.dashboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Widget {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 20)
    private String widgetType;
    private Integer minWidth;
    private Integer minHeight;

    @ManyToMany
    @ToString.Exclude
    @JoinTable(name = "widget_config",
            joinColumns = @JoinColumn(name = "widget_id"),
            inverseJoinColumns = @JoinColumn(name = "config_id"))
    private Set<Config> configs;

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;
}
