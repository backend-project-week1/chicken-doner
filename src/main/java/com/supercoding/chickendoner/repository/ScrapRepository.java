package com.supercoding.chickendoner.repository;

import com.supercoding.chickendoner.entity.Chicken;
import com.supercoding.chickendoner.entity.Scrap;
import com.supercoding.chickendoner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    //scrap 에 userId, chickenId가 존재하는가 여부
    Boolean existsByChickenIdAndUserId(Long chickenIdx, Long useridx);

    Scrap findScrapByChickenIdAndUserId(Long chickenIdx, Long useridx);


}
