package com.supercoding.chickendoner.repository;

import com.supercoding.chickendoner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String loginId);
    Optional<User> findByUsername(String username);

}
