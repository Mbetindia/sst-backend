package com.sst.sst_backend.dto;

public class StudentResponse {

    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String className;
    private String schoolName;
    private String city;
    private String status;
    private String teacherName;

    public StudentResponse() {
    }

    public StudentResponse(
            Long id,
            String name,
            String email,
            String mobile,
            String className,
            String schoolName,
            String city,
            String status,
            String teacherName
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.className = className;
        this.schoolName = schoolName;
        this.city = city;
        this.status = status;
        this.teacherName = teacherName;
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


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
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


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}