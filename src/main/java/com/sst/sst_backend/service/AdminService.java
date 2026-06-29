package com.sst.sst_backend.service;

import com.sst.sst_backend.dto.AdminDashboardResponse;
import com.sst.sst_backend.dto.RecentResultResponse;
import com.sst.sst_backend.entity.Result;
import com.sst.sst_backend.repository.ExamRepository;
import com.sst.sst_backend.repository.ResultRepository;
import com.sst.sst_backend.repository.StudentRepository;
import com.sst.sst_backend.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ExamRepository examRepository;
    private final ResultRepository resultRepository;

    public AdminService(
            StudentRepository studentRepository,
            TeacherRepository teacherRepository,
            ExamRepository examRepository,
            ResultRepository resultRepository
    ) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.examRepository = examRepository;
        this.resultRepository = resultRepository;
    }

    public AdminDashboardResponse getAdminDashboard() {

        long totalStudents = studentRepository.count();
        long totalTeachers = teacherRepository.count();
        long totalExams = examRepository.count();
        long completedExams = examRepository.countByStatus("Completed");
        long upcomingExams = examRepository.countByStatus("Upcoming");

        List<RecentResultResponse> recentResults = resultRepository
                .findTop5ByOrderByIdDesc()
                .stream()
                .map(this::mapToRecentResultResponse)
                .collect(Collectors.toList());

        return new AdminDashboardResponse(
                totalStudents,
                totalTeachers,
                totalExams,
                completedExams,
                upcomingExams,
                recentResults
        );
    }

    private RecentResultResponse mapToRecentResultResponse(Result result) {

        String studentName = result.getStudent() != null
                ? result.getStudent().getName()
                : "Unknown Student";

        String examName = result.getExam() != null
                ? result.getExam().getTitle()
                : "Unknown Exam";

        return new RecentResultResponse(
                studentName,
                examName,
                result.getPercentage(),
                result.getRank(),
                result.getPerformance()
        );
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
}