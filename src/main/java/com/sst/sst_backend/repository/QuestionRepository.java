package com.sst.sst_backend.repository;

import com.sst.sst_backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByExamIdOrderByIdAsc(Long examId);

    long countByExamId(Long examId);
}