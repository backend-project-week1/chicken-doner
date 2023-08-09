package com.supercoding.chickendoner.repository;

import com.supercoding.chickendoner.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface 주환리뷰임시레파이토리 extends JpaRepository<Review, Long> {
    // ReiewReposioty를 만들면 충돌날까봐 임시로 만들었습니다.
    // ReiewRepository 파일이 없어서 임시로 만들었습니다.


    /* 실제 존재하는 리뷰인지 확인하는 코드*/
    boolean existsByIdAndIsDeletedFalse(Long reviewIdx);

}
