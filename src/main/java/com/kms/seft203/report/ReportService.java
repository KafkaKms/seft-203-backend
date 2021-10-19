package com.kms.seft203.report;

import com.kms.seft203.auth.User;
import com.kms.seft203.contact.Contact;
import com.kms.seft203.contact.ContactRepository;
import com.kms.seft203.dashboard.Dashboard;
import com.kms.seft203.dashboard.DashboardRepository;
import com.kms.seft203.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "SpellCheckingInspection"})
@Service
public class ReportService {
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Map<String, Integer> countFieldsOfCollection(String collectionName, String fieldName, User user) {
        collectionName = collectionName.toLowerCase(Locale.ROOT);
        fieldName = fieldName.toLowerCase(Locale.ROOT);

        List<Object> list;
        switch (collectionName) {
            case "contact": {
                list = countFieldsOfContact(fieldName);
                break;
            }
            case "dashboard": {
                list = countFieldsOfDashboard(fieldName, user);
                break;
            }
            case "task": {
                list = countFieldsOfTask(fieldName, user);
                break;
            }
            default:
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "There's no such collection");
        }

        return list.stream()
                .collect(
                        Collectors.groupingBy(
                                Object::toString,
                                Collectors.reducing(0, e -> 1, Integer::sum)
                        )
                );
    }

    private List<Object> countFieldsOfTask(String fieldName, User user) {
        if ("iscomplete".equals(fieldName)) {
            return List.copyOf(taskRepository.findAllCompletion(user.getId()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "There's no such fieldname");
    }

    private List<Object> countFieldsOfContact(String fieldName) {
        switch (fieldName) {
            case "firstname": {
                return contactRepository.findAll()
                        .stream()
                        .map(Contact::getFirstName)
                        .collect(Collectors.toList());
            }
            case "lastname": {
                return contactRepository.findAll()
                        .stream()
                        .map(Contact::getLastName)
                        .collect(Collectors.toList());
            }
            case "title": {
                return contactRepository.findAll()
                        .stream()
                        .map(Contact::getTitle)
                        .collect(Collectors.toList());
            }
            case "department": {
                return contactRepository.findAll()
                        .stream()
                        .map(Contact::getDepartment)
                        .collect(Collectors.toList());
            }
            case "project": {
                return contactRepository.findAll()
                        .stream()
                        .map(Contact::getProject)
                        .collect(Collectors.toList());
            }
            default:
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "There's no such field");
        }
    }

    private List<Object> countFieldsOfDashboard(String fieldName, User user) {
        switch (fieldName) {
            case "title": {
                return dashboardRepository.getAllByUserId(user.getId())
                        .stream()
                        .map(Dashboard::getTitle)
                        .collect(Collectors.toList());
            }
            case "layoutType": {
                return dashboardRepository.getAllByUserId(user.getId())
                        .stream()
                        .map(Dashboard::getLayoutType)
                        .collect(Collectors.toList());
            }
            default:
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "There's no such fieldname");
        }
    }
}
