package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sanction_entity_comparison_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerSanctionEntityComparisonHistory {

    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_customer_id",nullable = false)
    private EntityCustomer entityCustomer;

    @Column(name = "name_score")
    private BigDecimal nameScore;

    @Column(name = "address_score")
    private BigDecimal addressScore;

    @Column(name = "total_score")
    private BigDecimal totalScore;

    @Column(name = "match_status")
    private String matchStatus;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
}
