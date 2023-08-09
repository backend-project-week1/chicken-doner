package com.supercoding.chickendoner.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comment")
@SQLDelete(sql = "UPDATE comment as c SET c.is_deleted = true WHERE idx = ?")
public class Comment {
    @Id
    @Column(name = "idx", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_idx", nullable = false)
    private Long userIdx;

    @ManyToOne
    @JoinColumn(name = "review_idx", nullable = false)
    private Review reviewIdx;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

}