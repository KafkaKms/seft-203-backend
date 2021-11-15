package com.kms.seft203.report;

import com.kms.seft203.auth.User;
import com.kms.seft203.contact.Contact;
import com.kms.seft203.contact.ContactRepository;
import com.kms.seft203.dashboard.Dashboard;
import com.kms.seft203.dashboard.DashboardRepository;
import com.kms.seft203.task.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReportServiceTest {
    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private DashboardRepository dashboardRepository;

    @Autowired
    private ReportService reportService;

    private final List<Contact> contacts = List.of(
            new Contact(0L, "Peter", "Parker", "SE", "Avenger", "No Way Home", null, null),
            new Contact(1L, "Shang", "Chi", "SE", "Avenger", "Ten Rings", null, null),
            new Contact(2L, "Nick", "Fury", "SSE", "SHIELD", "Marvel", null, null)
    );

    private final List<Boolean> isCompletedTasks = List.of(true, true, true, false, true);

    private final User user = new User(0L, "user", "email", "password", null, null, null);

    @BeforeEach
    public void setUp() {
        when(contactRepository.findAll())
                .thenReturn(contacts);

        when(taskRepository.findAllCompletion(user.getId()))
                .thenReturn(isCompletedTasks);

        var dashboards = List.of(
                new Dashboard(0L, user, "Bar", "Grid", null),
                new Dashboard(1L, user, "Pie", "Horizontal", null)
        );

        when(dashboardRepository.getAllByUserId(user.getId()))
                .thenReturn(dashboards);
    }

    @Test
    void shouldCorrect_WhenCountByFirstNameInContact() {
        var collectionName = "contact";
        var fieldName = "firstname";

        var expect = Map.of(
                "Peter", 1,
                "Shang", 1,
                "Nick", 1
        );

        var actual = reportService.countFieldsOfCollection(collectionName, fieldName, user);

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void shouldCorrect_WhenCountByLastNameInContact() {
        var collectionName = "contact";
        var fieldName = "lastname";

        var expect = Map.of(
                "Parker", 1,
                "Chi", 1,
                "Fury", 1
        );

        var actual = reportService.countFieldsOfCollection(collectionName, fieldName, user);

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void shouldCorrect_WhenCountByTitleInContact() {
        var collectionName = "contact";
        var fieldName = "title";

        var expect = Map.of(
                "SE", 2,
                "SSE", 1
        );

        var actual = reportService.countFieldsOfCollection(collectionName, fieldName, user);

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void shouldCorrect_WhenCountByDepartmentInContact() {
        var collectionName = "contact";
        var fieldName = "department";

        var expect = Map.of(
                "Avenger", 2,
                "SHIELD", 1
        );

        var actual = reportService.countFieldsOfCollection(collectionName, fieldName, user);

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void shouldCorrect_WhenCountByProjectInContact() {
        var collectionName = "contact";
        var fieldName = "project";

        var expect = Map.of(
                "No Way Home", 1,
                "Ten Rings", 1,
                "Marvel", 1
        );

        var actual = reportService.countFieldsOfCollection(collectionName, fieldName, user);

        assertThat(actual).isEqualTo(expect);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void shouldCorrect_WhenCountByIsCompleteInTask() {
        var collectionName = "task";
        var fieldName = "iscomplete";

        var expect = Map.of(
                "true", 4,
                "false", 1
        );

        var actual = reportService.countFieldsOfCollection(collectionName, fieldName, user);

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void shouldCorrect_WhenCountTitleInDashboard() {
        var collectionName = "dashboard";
        var fieldName = "title";

        var expect = Map.of(
                "Bar", 1,
                "Pie", 1
        );

        var actual = reportService.countFieldsOfCollection(collectionName, fieldName, user);

        assertThat(actual).isEqualTo(expect);
    }

    @Test
    void shouldCorrect_WhenCountLayoutInDashboard() {
        var collectionName = "dashboard";
        var fieldName = "layoutType";

        var expect = Map.of(
                "Grid", 1,
                "Horizontal", 1
        );

        var actual = reportService.countFieldsOfCollection(collectionName, fieldName, user);

        assertThat(actual).isEqualTo(expect);
    }
}