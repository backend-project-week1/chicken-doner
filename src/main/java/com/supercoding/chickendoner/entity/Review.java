package com.supercoding.chickendoner.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_idx", nullable = false)
    private User userIdx;

    @Column(name = "chicken_idx", nullable = false)
    private Long chickenIdx;
    @Column(name = "title", nullable = false)
    private String title;
    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "point", nullable = false)
    private Double point;

    @Column(name = "file")
    private String file;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private Timestamp createAt;

    @CreationTimestamp
    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    public void updateContent(String content) {
        this.content = content;
    }
}