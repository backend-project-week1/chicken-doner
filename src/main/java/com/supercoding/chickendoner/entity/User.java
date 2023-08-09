package com.supercoding.chickendoner.entity;

import com.supercoding.chickendoner.security.UserRole;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user as u SET u.is_deleted = true WHERE idx = ?")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Lob
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @CreationTimestamp
    @Column(name = "create_at", nullable = false, updatable = false)
    private Timestamp createAt;

    @CreationTimestamp
    @Column(name = "updated_at", insertable = false)
    private Timestamp updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Transient
    private UserRole role;

}