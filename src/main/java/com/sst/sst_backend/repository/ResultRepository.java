package com.sst.sst_backend.repository;

import com.sst.sst_backend.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findTop5ByOrderByIdDesc();

    List<Result> findByStudentIdOrderByIdDesc(Long studentId);

    List<Result> findByExamIdOrderByPercentageDesc(Long examId);

    List<Result> findByExamIdOrderByIdDesc(Long examId);
}