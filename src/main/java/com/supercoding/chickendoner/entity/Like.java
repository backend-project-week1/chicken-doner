package com.supercoding.chickendoner.entity;

import com.supercoding.chickendoner.entity.embeddedId.LikeId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "like")
public class Like {

    @EmbeddedId
    private LikeId likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_idx")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("reviewId")
    @JoinColumn(name = "review_idx")
    private Review review;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
}
