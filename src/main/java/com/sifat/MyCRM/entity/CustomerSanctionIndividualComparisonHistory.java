package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sanction_individual_comparison_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerSanctionIndividualComparisonHistory {
    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "individual_customer_id",nullable = false)
    private IndividualCustomer individualCustomer;

    @Column(name = "name_score")
    private BigDecimal nameScore;

    @Column(name = "date_of_birth_score")
    private BigDecimal dateOfBirthScore;

    @Column(name = "document_score")
    private BigDecimal documentScore;

    @Column(name = "nationality_score")
    private BigDecimal nationalityScore;

    @Column(name = "address_score")
    private BigDecimal addressScore;

    @Column(name = "place_of_birth_score")
    private BigDecimal placeOfBirthScore;

    @Column(name = "total_score")
    private BigDecimal totalScore;

    @Column(name = "match_status")
    private String matchStatus;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

}
