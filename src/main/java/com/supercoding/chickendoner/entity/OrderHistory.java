package com.supercoding.chickendoner.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "order_history")
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx", nullable = false)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "scrap_idx", nullable = false)
    private Scrap scrapIdx;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "order_idx", nullable = false)
    private Order orderIdx;

}