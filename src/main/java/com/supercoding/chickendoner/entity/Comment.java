package com.supercoding.chickendoner.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @Column(name = "idx", nullable = false)
    private Long id;

    @Column(name = "user_idx", nullable = false)
    private Long userIdx;

    @Column(name = "review_idx", nullable = false)
    private Long reviewIdx;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;


    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Timestamp isDeleted;

}