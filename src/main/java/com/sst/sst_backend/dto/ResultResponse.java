package com.sst.sst_backend.dto;

import java.time.LocalDateTime;

public class ResultResponse {

    private Long id;

    private Long studentId;
    private String studentName;

    private Long examId;
    private String examName;

    private Integer totalQuestions;
    private Integer attemptedQuestions;
    private Integer correctAnswers;
    private Integer wrongAnswers;

    private Double score;
    private Double totalMarks;
    private Double percentage;

    private Integer rank;
    private String performance;

    private LocalDateTime createdAt;

    public ResultResponse() {
    }

    public ResultResponse(Long id, Long studentId, String studentName,
                          Long examId, String examName, Integer totalQuestions,
                          Integer attemptedQuestions, Integer correctAnswers,
                          Integer wrongAnswers, Double score, Double totalMarks,
                          Double percentage, Integer rank, String performance,
                          LocalDateTime createdAt) {
        this.id = id;
        this.studentId = studentId;
        this.studentName = studentName;
        this.examId = examId;
        this.examName = examName;
        this.totalQuestions = totalQuestions;
        this.attemptedQuestions = attemptedQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.score = score;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
        this.rank = rank;
        this.performance = performance;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public Long getExamId() {
        return examId;
    }

    public String getExamName() {
        return examName;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public Integer getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public Integer getWrongAnswers() {
        return wrongAnswers;
    }

    public Double getScore() {
        return score;
    }

    public Double getTotalMarks() {
        return totalMarks;
    }

    public Double getPercentage() {
        return percentage;
    }

    public Integer getRank() {
        return rank;
    }

    public String getPerformance() {
        return performance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}