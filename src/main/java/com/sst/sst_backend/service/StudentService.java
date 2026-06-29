package com.sst.sst_backend.service;

import com.sst.sst_backend.dto.StudentRequest;
import com.sst.sst_backend.dto.StudentResponse;
import com.sst.sst_backend.entity.Student;
import com.sst.sst_backend.entity.User;
import com.sst.sst_backend.enums.Role;
import com.sst.sst_backend.exception.BadRequestException;
import com.sst.sst_backend.exception.EmailAlreadyExistsException;
import com.sst.sst_backend.exception.StudentNotFoundException;
import com.sst.sst_backend.repository.StudentRepository;
import com.sst.sst_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(
            StudentRepository studentRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public StudentResponse createStudent(StudentRequest request) {

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BadRequestException("Password is required");
        }

        if (studentRepository.existsByEmail(request.getEmail()) || userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(encodedPassword);
        student.setMobile(request.getMobile());
        student.setClassName(request.getClassName());
        student.setSchoolName(request.getSchoolName());
        student.setCity(request.getCity());
        student.setStatus(request.getStatus() != null && !request.getStatus().isBlank()
                ? request.getStatus()
                : "Active");

        Student savedStudent = studentRepository.save(student);

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(Role.STUDENT);

        userRepository.save(user);

        return mapToResponse(savedStudent);
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public StudentResponse getStudentById(Long id) {
        Student student = getStudentEntityById(id);
        return mapToResponse(student);
    }

    public StudentResponse updateStudent(Long id, StudentRequest request) {

        Student student = getStudentEntityById(id);

        String oldEmail = student.getEmail();

        if (!oldEmail.equalsIgnoreCase(request.getEmail())) {
            if (studentRepository.existsByEmail(request.getEmail()) || userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("Email already registered");
            }
        }

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setMobile(request.getMobile());
        student.setClassName(request.getClassName());
        student.setSchoolName(request.getSchoolName());
        student.setCity(request.getCity());
        student.setStatus(request.getStatus() != null && !request.getStatus().isBlank()
                ? request.getStatus()
                : student.getStatus());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            student.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Student updatedStudent = studentRepository.save(student);

        userRepository.findByEmail(oldEmail).ifPresent(user -> {
            user.setName(request.getName());
            user.setEmail(request.getEmail());

            if (request.getPassword() != null && !request.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            user.setRole(Role.STUDENT);
            userRepository.save(user);
        });

        return mapToResponse(updatedStudent);
    }

    public void deleteStudent(Long id) {

        Student student = getStudentEntityById(id);

        userRepository.findByEmail(student.getEmail())
                .ifPresent(userRepository::delete);

        studentRepository.delete(student);
    }

    private Student getStudentEntityById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    private StudentResponse mapToResponse(Student student) {

        String teacherName = student.getTeacher() != null
                ? student.getTeacher().getName()
                : null;

        return new StudentResponse(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getMobile(),
                student.getClassName(),
                student.getSchoolName(),
                student.getCity(),
                student.getStatus(),
                teacherName
        );
    }
}