package com.sst.sst_backend.service;

import com.sst.sst_backend.dto.ExamRequest;
import com.sst.sst_backend.dto.ExamResponse;
import com.sst.sst_backend.entity.Exam;
import com.sst.sst_backend.exception.BadRequestException;
import com.sst.sst_backend.exception.ExamNotFoundException;
import com.sst.sst_backend.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public ExamResponse createExam(ExamRequest request) {

        if (examRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new BadRequestException("Exam title already exists");
        }

        Exam exam = new Exam();
        exam.setTitle(request.getTitle());
        exam.setCategory(request.getCategory());
        exam.setClassName(request.getClassName());
        exam.setDuration(request.getDuration());
        exam.setTotalMarks(request.getTotalMarks());
        exam.setStatus(validateAndGetStatus(request.getStatus()));

        Exam savedExam = examRepository.save(exam);

        return mapToResponse(savedExam);
    }

    public List<ExamResponse> getAllExams() {
        return examRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ExamResponse getExamById(Long id) {
        Exam exam = getExamEntityById(id);
        return mapToResponse(exam);
    }

    public ExamResponse updateExam(Long id, ExamRequest request) {

        Exam exam = getExamEntityById(id);

        if (!exam.getTitle().equalsIgnoreCase(request.getTitle())) {
            if (examRepository.existsByTitleIgnoreCase(request.getTitle())) {
                throw new BadRequestException("Exam title already exists");
            }
        }

        exam.setTitle(request.getTitle());
        exam.setCategory(request.getCategory());
        exam.setClassName(request.getClassName());
        exam.setDuration(request.getDuration());
        exam.setTotalMarks(request.getTotalMarks());
        exam.setStatus(validateAndGetStatus(request.getStatus()));

        Exam updatedExam = examRepository.save(exam);

        return mapToResponse(updatedExam);
    }

    public void deleteExam(Long id) {
        Exam exam = getExamEntityById(id);
        examRepository.delete(exam);
    }

    private Exam getExamEntityById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new ExamNotFoundException("Exam not found with id: " + id));
    }

    private String validateAndGetStatus(String status) {

        if (status == null || status.isBlank()) {
            return "Upcoming";
        }

        if (
                status.equalsIgnoreCase("Upcoming") ||
                        status.equalsIgnoreCase("Completed")
        ) {
            return status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        }

        throw new BadRequestException("Invalid exam status. Allowed values: Upcoming, Completed");
    }

    private ExamResponse mapToResponse(Exam exam) {
        return new ExamResponse(
                exam.getId(),
                exam.getTitle(),
                exam.getCategory(),
                exam.getClassName(),
                exam.getDuration(),
                exam.getTotalMarks(),
                exam.getStatus()
        );
    }
}