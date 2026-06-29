package com.sst.sst_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ResultRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Exam ID is required")
    private Long examId;

    @NotNull(message = "Total questions are required")
    @Min(value = 1, message = "Total questions must be at least 1")
    private Integer totalQuestions;

    @NotNull(message = "Attempted questions are required")
    @Min(value = 0, message = "Attempted questions cannot be negative")
    private Integer attemptedQuestions;

    @NotNull(message = "Correct answers are required")
    @Min(value = 0, message = "Correct answers cannot be negative")
    private Integer correctAnswers;

    @NotNull(message = "Wrong answers are required")
    @Min(value = 0, message = "Wrong answers cannot be negative")
    private Integer wrongAnswers;

    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Score cannot be negative")
    private Double score;

    public ResultRequest() {
    }

    public ResultRequest(Long studentId, Long examId, Integer totalQuestions,
                         Integer attemptedQuestions, Integer correctAnswers,
                         Integer wrongAnswers, Double score) {
        this.studentId = studentId;
        this.examId = examId;
        this.totalQuestions = totalQuestions;
        this.attemptedQuestions = attemptedQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.score = score;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public Integer getAttemptedQuestions() {
        return attemptedQuestions;
    }

    public void setAttemptedQuestions(Integer attemptedQuestions) {
        this.attemptedQuestions = attemptedQuestions;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Integer getWrongAnswers() {
        return wrongAnswers;
    }

    public void setWrongAnswers(Integer wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}