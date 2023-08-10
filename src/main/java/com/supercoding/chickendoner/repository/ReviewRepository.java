package com.supercoding.chickendoner.repository;

import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.Scrap;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByIsDeletedEquals(boolean isDeleted, Sort sort);

    /* 실제 존재하는 리뷰인지 확인하는 코드*/
    boolean existsByIdAndIsDeletedFalse(Long reviewIdx);

    List<Review> findAllByIsDeletedEqualsAndUserId(Boolean isDeleted, Long userIdx ,Sort createdAt);

}
