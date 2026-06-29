package com.sst.sst_backend.dto;

import java.util.List;

public class AdminDashboardResponse {

    private long totalStudents;
    private long totalTeachers;
    private long totalExams;
    private long completedExams;
    private long upcomingExams;
    private List<RecentResultResponse> recentResults;

    public AdminDashboardResponse() {
    }

    public AdminDashboardResponse(
            long totalStudents,
            long totalTeachers,
            long totalExams,
            long completedExams,
            long upcomingExams,
            List<RecentResultResponse> recentResults
    ) {
        this.totalStudents = totalStudents;
        this.totalTeachers = totalTeachers;
        this.totalExams = totalExams;
        this.completedExams = completedExams;
        this.upcomingExams = upcomingExams;
        this.recentResults = recentResults;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalTeachers() {
        return totalTeachers;
    }

    public void setTotalTeachers(long totalTeachers) {
        this.totalTeachers = totalTeachers;
    }

    public long getTotalExams() {
        return totalExams;
    }

    public void setTotalExams(long totalExams) {
        this.totalExams = totalExams;
    }

    public long getCompletedExams() {
        return completedExams;
    }

    public void setCompletedExams(long completedExams) {
        this.completedExams = completedExams;
    }

    public long getUpcomingExams() {
        return upcomingExams;
    }

    public void setUpcomingExams(long upcomingExams) {
        this.upcomingExams = upcomingExams;
    }

    public List<RecentResultResponse> getRecentResults() {
        return recentResults;
    }

    public void setRecentResults(List<RecentResultResponse> recentResults) {
        this.recentResults = recentResults;
    }
}