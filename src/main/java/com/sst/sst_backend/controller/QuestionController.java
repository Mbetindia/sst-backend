package com.sst.sst_backend.controller;

import com.sst.sst_backend.dto.QuestionRequest;
import com.sst.sst_backend.dto.QuestionResponse;
import com.sst.sst_backend.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/exams/{examId}/questions")
    public ResponseEntity<QuestionResponse> addQuestion(
            @PathVariable Long examId,
            @Valid @RequestBody QuestionRequest request
    ) {
        QuestionResponse response = questionService.addQuestion(examId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/exams/{examId}/questions")
    public ResponseEntity<List<QuestionResponse>> getQuestionsByExam(
            @PathVariable Long examId
    ) {
        List<QuestionResponse> response = questionService.getQuestionsByExam(examId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/questions/{questionId}")
    public ResponseEntity<QuestionResponse> getQuestionById(
            @PathVariable Long questionId
    ) {
        QuestionResponse response = questionService.getQuestionById(questionId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<QuestionResponse> updateQuestion(
            @PathVariable Long questionId,
            @Valid @RequestBody QuestionRequest request
    ) {
        QuestionResponse response = questionService.updateQuestion(questionId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<String> deleteQuestion(
            @PathVariable Long questionId
    ) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok("Question deleted successfully");
    }
}