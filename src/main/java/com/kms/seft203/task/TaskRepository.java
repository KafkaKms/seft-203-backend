package com.kms.seft203.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select t.isCompleted from Task t inner join User u on u.id = :userId")
    List<Boolean> findAllCompletion(Long userId);
}
