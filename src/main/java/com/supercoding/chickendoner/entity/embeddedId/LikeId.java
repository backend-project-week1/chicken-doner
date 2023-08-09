package com.supercoding.chickendoner.entity.embeddedId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeId implements Serializable {

    @Column(name = "user_idx")
    private Long userId;

    @Column(name = "review_idx")
    private Long reviewId;

}
