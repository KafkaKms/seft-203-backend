package com.kms.seft203.task;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected String task; // NOSONAR
    protected Boolean isCompleted;
    @Column(length = 20)
    protected String userId;

    public static Task of(SaveTaskRequest saveTaskRequest) {
        return new Task(null, saveTaskRequest.getTask(), false, saveTaskRequest.getUserId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var item = (Task) o;
        return id != null && Objects.equals(id, item.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
