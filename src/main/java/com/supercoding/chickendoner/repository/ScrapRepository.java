package com.supercoding.chickendoner.repository;

import com.supercoding.chickendoner.entity.Scrap;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    //scrap 에 userId, chickenId가 존재하는가 여부
    Boolean existsByChickenIdAndUserId(Long chickenIdx, Long userIdx);

    //치킨 아이디와 유저 아이디를 가지고 생성된 스크랩(장바구니) 불러오기
    Scrap findScrapByChickenIdAndUserId(Long chickenIdx, Long userIdx);

    //userId 를 넣어서 isDeleted = false 인 스크랩들만 찾아오기
    List<Scrap> findAllByIsDeletedEqualsAndUserId(Boolean isDeleted, Long userIdx, Sort createdAt);


}
