package com.sst.sst_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class TeacherRequest {

    @NotBlank(message = "Teacher name is required")
    private String name;

    @Email(message = "Enter valid email")
    @NotBlank(message = "Email is required")
    private String email;

    private String password;

    private String mobile;

    @NotBlank(message = "Subject is required")
    private String subject;

    private String qualification;

    private String city;

    private String status;

    public TeacherRequest() {
    }

    public TeacherRequest(
            String name,
            String email,
            String password,
            String mobile,
            String subject,
            String qualification,
            String city,
            String status
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.subject = subject;
        this.qualification = qualification;
        this.city = city;
        this.status = status;
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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}