package com.supercoding.chickendoner.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "scrap")
public class Scrap {
    @Id
    @Column(name = "idx", nullable = false)
    private Long id;

    @Column(name = "user_idx", nullable = false)
    private Long userIdx;

    @Column(name = "chicken_idx", nullable = false)
    private Long chickenIdx;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @CreationTimestamp
    @Column(name = "is_deleted", nullable = false, insertable = false)
    private Boolean isDeleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

}