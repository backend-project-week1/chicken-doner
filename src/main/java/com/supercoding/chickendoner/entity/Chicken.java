package com.supercoding.chickendoner.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chicken")
public class Chicken {
    @Id
    @Column(name = "idx", nullable = false)
    private Long id;

    @Column(name = "chiken_name", nullable = false, length = 50)
    private String chikenName;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @CreationTimestamp //생성되었을 때 시간을 자동으로 만들어준다
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @CreationTimestamp
    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;

}