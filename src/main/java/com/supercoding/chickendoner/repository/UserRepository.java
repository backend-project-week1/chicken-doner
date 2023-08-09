package com.supercoding.chickendoner.repository;

import com.supercoding.chickendoner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String loginId);
    Optional<User> findByUsername(String username);

    @Modifying
    @Query(value = "UPDATE User as u " +
            "SET u.isDeleted = true " +
            "WHERE 1=1" +
            "AND u.id = :userIdx")
    void deleteUser(@Param("userIdx") Long userIdx);

}
