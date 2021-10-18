package com.kms.seft203.dashboard;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kms.seft203.auth.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 20)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @JsonBackReference
    private User user;

    @Column(length = 20)
    private String title;

    @Column(length = 20)
    private String layoutType;

    @OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Widget> widgets;

    public static Dashboard of(SaveDashboardRequest saveDashboardRequest, User user) {
        return new Dashboard(
                0L,
                user,
                saveDashboardRequest.getTitle(),
                saveDashboardRequest.getLayoutType(),
                saveDashboardRequest.getWidgets()
        );
    }
}
