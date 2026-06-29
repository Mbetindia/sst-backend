package com.sst.sst_backend.repository;

import com.sst.sst_backend.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    long countByStatus(String status);

    boolean existsByTitleIgnoreCase(String title);
}