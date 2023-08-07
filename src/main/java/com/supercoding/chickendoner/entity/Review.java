package com.supercoding.chickendoner.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @Column(name = "user_idx", nullable = false)
    private Long userIdx;

    @Column(name = "chicken_idx", nullable = false)
    private Long chickenIdx;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "point", nullable = false)
    private Double point;

    @Column(name = "file")
    private String file;

    @Column(name = "create_at", nullable = false)
    private Instant createAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

}