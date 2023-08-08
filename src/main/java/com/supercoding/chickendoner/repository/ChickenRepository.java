package com.supercoding.chickendoner.repository;

import com.supercoding.chickendoner.entity.Chicken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChickenRepository extends JpaRepository<Chicken, Long> {


}
