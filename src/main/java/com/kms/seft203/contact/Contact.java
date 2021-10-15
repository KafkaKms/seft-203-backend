package com.kms.seft203.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 30)
//    @NotBlank
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(length = 30)
    private String department;

    @Column(length = 30)
    private String project;

    private String avatar;

    @Column(nullable = false)
    private Integer employeeId;

    public static Contact of(SaveContactRequest saveTaskRequest) {
        return new Contact(0L,
                saveTaskRequest.getFirstName(),
                saveTaskRequest.getLastName(),
                saveTaskRequest.getTitle(),
                saveTaskRequest.getDepartment(),
                saveTaskRequest.getProject(),
                saveTaskRequest.getTitle(),
                saveTaskRequest.getEmployeeId());
    }
}
