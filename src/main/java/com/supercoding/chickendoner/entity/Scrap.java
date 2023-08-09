package com.supercoding.chickendoner.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scrap")
public class Scrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_idx", nullable = false)
    private User user;   //장바구니는 치킨 등록 시 생성, user_idx는 유저의 id로 장바구니 생성 시 불러옵니다.

    @ManyToOne
    @JoinColumn(name="chicken_idx", nullable = false)
    private Chicken chicken;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;






}