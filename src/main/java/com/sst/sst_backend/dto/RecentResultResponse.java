package com.sst.sst_backend.dto;

public class RecentResultResponse {

    private String studentName;
    private String examName;
    private double percentage;
    private int rank;
    private String performance;

    public RecentResultResponse() {
    }

    public RecentResultResponse(
            String studentName,
            String examName,
            double percentage,
            int rank,
            String performance
    ) {
        this.studentName = studentName;
        this.examName = examName;
        this.percentage = percentage;
        this.rank = rank;
        this.performance = performance;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getPerformance() {
        return performance;
    }

    public void setPerformance(String performance) {
        this.performance = performance;
    }
}