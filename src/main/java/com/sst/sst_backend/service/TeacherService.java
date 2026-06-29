package com.sst.sst_backend.service;

import com.sst.sst_backend.dto.TeacherRequest;
import com.sst.sst_backend.dto.TeacherResponse;
import com.sst.sst_backend.entity.Student;
import com.sst.sst_backend.entity.Teacher;
import com.sst.sst_backend.entity.User;
import com.sst.sst_backend.enums.Role;
import com.sst.sst_backend.exception.BadRequestException;
import com.sst.sst_backend.exception.EmailAlreadyExistsException;
import com.sst.sst_backend.exception.TeacherNotFoundException;
import com.sst.sst_backend.repository.StudentRepository;
import com.sst.sst_backend.repository.TeacherRepository;
import com.sst.sst_backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherService(
            TeacherRepository teacherRepository,
            StudentRepository studentRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public TeacherResponse createTeacher(TeacherRequest request) {

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BadRequestException("Password is required");
        }

        if (teacherRepository.existsByEmail(request.getEmail()) || userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Teacher teacher = new Teacher();
        teacher.setName(request.getName());
        teacher.setEmail(request.getEmail());
        teacher.setPassword(encodedPassword);
        teacher.setMobile(request.getMobile());
        teacher.setSubject(request.getSubject());
        teacher.setQualification(request.getQualification());
        teacher.setCity(request.getCity());
        teacher.setStatus(request.getStatus() != null && !request.getStatus().isBlank()
                ? request.getStatus()
                : "Active");

        Teacher savedTeacher = teacherRepository.save(teacher);

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(Role.TEACHER);

        userRepository.save(user);

        return mapToResponse(savedTeacher);
    }

    public List<TeacherResponse> getAllTeachers() {
        return teacherRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public TeacherResponse getTeacherById(Long id) {
        Teacher teacher = getTeacherEntityById(id);
        return mapToResponse(teacher);
    }

    public TeacherResponse updateTeacher(Long id, TeacherRequest request) {

        Teacher teacher = getTeacherEntityById(id);

        String oldEmail = teacher.getEmail();

        if (!oldEmail.equalsIgnoreCase(request.getEmail())) {
            if (teacherRepository.existsByEmail(request.getEmail()) || userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("Email already registered");
            }
        }

        teacher.setName(request.getName());
        teacher.setEmail(request.getEmail());
        teacher.setMobile(request.getMobile());
        teacher.setSubject(request.getSubject());
        teacher.setQualification(request.getQualification());
        teacher.setCity(request.getCity());
        teacher.setStatus(request.getStatus() != null && !request.getStatus().isBlank()
                ? request.getStatus()
                : teacher.getStatus());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            teacher.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Teacher updatedTeacher = teacherRepository.save(teacher);

        userRepository.findByEmail(oldEmail).ifPresent(user -> {
            user.setName(request.getName());
            user.setEmail(request.getEmail());

            if (request.getPassword() != null && !request.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }

            user.setRole(Role.TEACHER);
            userRepository.save(user);
        });

        return mapToResponse(updatedTeacher);
    }

    public void deleteTeacher(Long id) {

        Teacher teacher = getTeacherEntityById(id);

        List<Student> assignedStudents = teacher.getStudents();

        if (assignedStudents != null && !assignedStudents.isEmpty()) {
            assignedStudents.forEach(student -> student.setTeacher(null));
            studentRepository.saveAll(assignedStudents);
            assignedStudents.clear();
        }

        userRepository.findByEmail(teacher.getEmail())
                .ifPresent(userRepository::delete);

        teacherRepository.delete(teacher);
    }

    private Teacher getTeacherEntityById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with id: " + id));
    }

    private TeacherResponse mapToResponse(Teacher teacher) {

        int assignedStudentsCount = teacher.getStudents() != null
                ? teacher.getStudents().size()
                : 0;

        return new TeacherResponse(
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getMobile(),
                teacher.getSubject(),
                teacher.getQualification(),
                teacher.getCity(),
                teacher.getStatus(),
                assignedStudentsCount
        );
    }
}