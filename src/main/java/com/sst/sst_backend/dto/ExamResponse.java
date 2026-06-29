package com.sst.sst_backend.dto;

public class ExamResponse {

    private Long id;
    private String title;
    private String category;
    private String className;
    private Integer duration;
    private Integer totalMarks;
    private String status;

    public ExamResponse() {
    }

    public ExamResponse(Long id, String title, String category, String className, Integer duration, Integer totalMarks, String status) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.className = className;
        this.duration = duration;
        this.totalMarks = totalMarks;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}