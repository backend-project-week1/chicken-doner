package com.supercoding.chickendoner.repository;

import com.supercoding.chickendoner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
