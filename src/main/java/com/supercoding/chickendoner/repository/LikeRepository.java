package com.supercoding.chickendoner.repository;

import com.supercoding.chickendoner.entity.Likes;
import com.supercoding.chickendoner.entity.embeddedId.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likes, LikeId> {

//    Optional<Like> findByLikeId_ReviewIdAndLikeId_UserId(Long likeId_reviewId, Long likeId_userId);

    @Query(value = "SELECT l FROM Likes as l WHERE l.likeId.reviewId = :reviewIdx AND l.likeId.userId = :userIdx")
    Optional<Likes> findByLike(@Param("reviewIdx")Long reviewIdx, @Param("userIdx")Long userIdx);

    @Modifying
    @Query(value = "UPDATE Likes as l SET l.isDeleted =:isDeleted WHERE l.likeId.userId=:userId AND l.likeId.reviewId=:reviewId")
    void updateLikeStatusByUserAndReview(boolean isDeleted, Long userId, Long reviewId);

}
