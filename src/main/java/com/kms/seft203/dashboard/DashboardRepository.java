package com.kms.seft203.dashboard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
    @Query("select distinct d from Dashboard d where d.user.id = :id")
    List<Dashboard> getAllByUserId(Long id);
}
