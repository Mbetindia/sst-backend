package com.sst.sst_backend.dto;

public class TeacherResponse {

    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String subject;
    private String qualification;
    private String city;
    private String status;
    private int assignedStudentsCount;

    public TeacherResponse() {
    }

    public TeacherResponse(
            Long id,
            String name,
            String email,
            String mobile,
            String subject,
            String qualification,
            String city,
            String status,
            int assignedStudentsCount
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.subject = subject;
        this.qualification = qualification;
        this.city = city;
        this.status = status;
        this.assignedStudentsCount = assignedStudentsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getAssignedStudentsCount() {
        return assignedStudentsCount;
    }

    public void setAssignedStudentsCount(int assignedStudentsCount) {
        this.assignedStudentsCount = assignedStudentsCount;
    }
}