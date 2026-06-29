package com.sst.sst_backend.repository;

import com.sst.sst_backend.entity.User;
import com.sst.sst_backend.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndRole(String email, Role role);

    boolean existsByEmail(String email);
}