package com.sst.sst_backend.controller;

import com.sst.sst_backend.dto.ExamRequest;
import com.sst.sst_backend.dto.ExamResponse;
import com.sst.sst_backend.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/exams")
@CrossOrigin(origins = "http://localhost:5173")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<ExamResponse> createExam(
            @Valid @RequestBody ExamRequest request
    ) {
        ExamResponse response = examService.createExam(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExamResponse>> getAllExams() {
        List<ExamResponse> response = examService.getAllExams();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResponse> getExamById(
            @PathVariable Long id
    ) {
        ExamResponse response = examService.getExamById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamResponse> updateExam(
            @PathVariable Long id,
            @Valid @RequestBody ExamRequest request
    ) {
        ExamResponse response = examService.updateExam(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExam(
            @PathVariable Long id
    ) {
        examService.deleteExam(id);
        return ResponseEntity.ok("Exam deleted successfully");
    }
}