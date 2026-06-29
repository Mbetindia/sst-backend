package com.sst.sst_backend.controller;

import com.sst.sst_backend.dto.ResultRequest;
import com.sst.sst_backend.dto.ResultResponse;
import com.sst.sst_backend.service.ResultService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping("/results/generate")
    public ResponseEntity<ResultResponse> generateResult(
            @Valid @RequestBody ResultRequest request
    ) {
        ResultResponse response = resultService.generateResult(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/admin/results")
    public ResponseEntity<List<ResultResponse>> getAllResults() {
        List<ResultResponse> response = resultService.getAllResults();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/results/recent")
    public ResponseEntity<List<ResultResponse>> getRecentResults() {
        List<ResultResponse> response = resultService.getRecentResults();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/results/{id}")
    public ResponseEntity<ResultResponse> getResultById(
            @PathVariable Long id
    ) {
        ResultResponse response = resultService.getResultById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/results/student/{studentId}")
    public ResponseEntity<List<ResultResponse>> getResultsByStudent(
            @PathVariable Long studentId
    ) {
        List<ResultResponse> response = resultService.getResultsByStudent(studentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/results/exam/{examId}")
    public ResponseEntity<List<ResultResponse>> getResultsByExam(
            @PathVariable Long examId
    ) {
        List<ResultResponse> response = resultService.getResultsByExam(examId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/results/{id}")
    public ResponseEntity<String> deleteResult(
            @PathVariable Long id
    ) {
        resultService.deleteResult(id);
        return ResponseEntity.ok("Result deleted successfully");
    }
}