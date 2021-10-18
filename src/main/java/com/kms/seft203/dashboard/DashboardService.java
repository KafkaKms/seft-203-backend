package com.kms.seft203.dashboard;

import com.kms.seft203.auth.User;
import com.kms.seft203.exceptions.DataNotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Service
public class DashboardService {
    @Autowired
    private DashboardRepository dashboardRepository;

    public List<Dashboard> getAllByUserId(Long id) {
        return dashboardRepository.getAllByUserId(id);
    }

    public Dashboard update(Long id, SaveDashboardRequest saveDashboardRequest, User user) {
        if (!dashboardRepository.existsById(id)) {
            throw new DataNotFoundException("Can't find such dashboard id!");
        }

        var dashboard = Dashboard.of(saveDashboardRequest, user);
        dashboard.setId(id);

        if (ObjectUtils.isNotEmpty(saveDashboardRequest.getWidgets())) {
            for (var widget : saveDashboardRequest.getWidgets()) {
                widget.setDashboard(dashboard);
            }
        }

        return dashboardRepository.save(dashboard);
    }

    public Dashboard create(SaveDashboardRequest saveDashboardRequest, User user) {
        return dashboardRepository.save(Dashboard.of(saveDashboardRequest, user));
    }
}
