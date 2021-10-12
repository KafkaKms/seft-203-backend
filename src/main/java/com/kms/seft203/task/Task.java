package com.kms.seft203.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String task; // NOSONAR
    private Boolean isCompleted;
    private String userId;

    public static Task of(SaveTaskRequest saveTaskRequest) {
        return new Task(null, saveTaskRequest.getTask(), saveTaskRequest.getIsCompleted(), saveTaskRequest.getUserId());
    }
}
