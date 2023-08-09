package com.supercoding.chickendoner.repository;

import com.supercoding.chickendoner.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /* 해당 리뷰에 대한 댓글 전체조회(삭제 안된 것) */
    List<Comment> findAllByReviewIdx_IdAndIsDeletedFalse(Long reviewIdxS);

    /* 리뷰에 대한 댓글이 있는지 확인하는 코드*/
    boolean existsByIdAndIsDeletedFalse(Long reviewIdx);


}
