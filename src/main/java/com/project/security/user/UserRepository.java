package com.project.security.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
    //optional - A container object which may or may not contain a non-null value.
    Optional<User> findByEmail(String email);
}
