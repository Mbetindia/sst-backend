package com.sst.sst_backend.service;

import com.sst.sst_backend.dto.ResultRequest;
import com.sst.sst_backend.dto.ResultResponse;
import com.sst.sst_backend.entity.Exam;
import com.sst.sst_backend.entity.Result;
import com.sst.sst_backend.entity.Student;
import com.sst.sst_backend.exception.BadRequestException;
import com.sst.sst_backend.exception.ExamNotFoundException;
import com.sst.sst_backend.exception.ResultNotFoundException;
import com.sst.sst_backend.exception.StudentNotFoundException;
import com.sst.sst_backend.repository.ExamRepository;
import com.sst.sst_backend.repository.ResultRepository;
import com.sst.sst_backend.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;

    public ResultService(
            ResultRepository resultRepository,
            StudentRepository studentRepository,
            ExamRepository examRepository
    ) {
        this.resultRepository = resultRepository;
        this.studentRepository = studentRepository;
        this.examRepository = examRepository;
    }

    public ResultResponse generateResult(ResultRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException(
                        "Student not found with id: " + request.getStudentId()
                ));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new ExamNotFoundException(
                        "Exam not found with id: " + request.getExamId()
                ));

        validateResultRequest(request, exam);

        double totalMarks = exam.getTotalMarks();
        double percentage = (request.getScore() / totalMarks) * 100;

        Result result = new Result();
        result.setStudent(student);
        result.setExam(exam);
        result.setTotalQuestions(request.getTotalQuestions());
        result.setAttemptedQuestions(request.getAttemptedQuestions());
        result.setCorrectAnswers(request.getCorrectAnswers());
        result.setWrongAnswers(request.getWrongAnswers());
        result.setScore(request.getScore());
        result.setTotalMarks(totalMarks);
        result.setPercentage(roundToTwoDecimalPlaces(percentage));
        result.setPerformance(calculatePerformance(percentage));
        result.setRank(0);
        result.setCreatedAt(LocalDateTime.now());

        Result savedResult = resultRepository.save(result);

        updateRanksForExam(exam.getId());

        Result updatedResult = resultRepository.findById(savedResult.getId())
                .orElseThrow(() -> new ResultNotFoundException(
                        "Result not found with id: " + savedResult.getId()
                ));

        return mapToResponse(updatedResult);
    }

    public List<ResultResponse> getAllResults() {
        return resultRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ResultResponse> getRecentResults() {
        return resultRepository
                .findTop5ByOrderByIdDesc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ResultResponse getResultById(Long id) {
        Result result = getResultEntityById(id);
        return mapToResponse(result);
    }

    public List<ResultResponse> getResultsByStudent(Long studentId) {

        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException("Student not found with id: " + studentId);
        }

        return resultRepository
                .findByStudentIdOrderByIdDesc(studentId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ResultResponse> getResultsByExam(Long examId) {

        if (!examRepository.existsById(examId)) {
            throw new ExamNotFoundException("Exam not found with id: " + examId);
        }

        return resultRepository
                .findByExamIdOrderByPercentageDesc(examId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteResult(Long id) {
        Result result = getResultEntityById(id);
        Long examId = result.getExam() != null ? result.getExam().getId() : null;

        resultRepository.delete(result);

        if (examId != null) {
            updateRanksForExam(examId);
        }
    }

    private void updateRanksForExam(Long examId) {

        List<Result> results = resultRepository.findByExamIdOrderByPercentageDesc(examId);

        int rank = 1;

        for (Result result : results) {
            result.setRank(rank);
            rank++;
        }

        resultRepository.saveAll(results);
    }

    private Result getResultEntityById(Long id) {
        return resultRepository.findById(id)
                .orElseThrow(() -> new ResultNotFoundException("Result not found with id: " + id));
    }

    private void validateResultRequest(ResultRequest request, Exam exam) {

        if (request.getAttemptedQuestions() > request.getTotalQuestions()) {
            throw new BadRequestException("Attempted questions cannot be greater than total questions");
        }

        if (request.getCorrectAnswers() + request.getWrongAnswers() > request.getAttemptedQuestions()) {
            throw new BadRequestException("Correct and wrong answers cannot be greater than attempted questions");
        }

        if (exam.getTotalMarks() == null || exam.getTotalMarks() <= 0) {
            throw new BadRequestException("Exam total marks are invalid");
        }

        if (request.getScore() > exam.getTotalMarks()) {
            throw new BadRequestException("Score cannot be greater than exam total marks");
        }
    }

    private String calculatePerformance(double percentage) {
        if (percentage >= 90) {
            return "Excellent";
        } else if (percentage >= 75) {
            return "Very Good";
        } else if (percentage >= 60) {
            return "Good";
        } else if (percentage >= 40) {
            return "Average";
        } else {
            return "Needs Improvement";
        }
    }

    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private ResultResponse mapToResponse(Result result) {

        Long studentId = result.getStudent() != null
                ? result.getStudent().getId()
                : null;

        String studentName = result.getStudent() != null
                ? result.getStudent().getName()
                : "Unknown Student";

        Long examId = result.getExam() != null
                ? result.getExam().getId()
                : null;

        String examName = result.getExam() != null
                ? result.getExam().getTitle()
                : "Unknown Exam";

        return new ResultResponse(
                result.getId(),
                studentId,
                studentName,
                examId,
                examName,
                result.getTotalQuestions(),
                result.getAttemptedQuestions(),
                result.getCorrectAnswers(),
                result.getWrongAnswers(),
                result.getScore(),
                result.getTotalMarks(),
                result.getPercentage(),
                result.getRank(),
                result.getPerformance(),
                result.getCreatedAt()
        );
    }
}