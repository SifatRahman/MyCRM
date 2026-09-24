package com.sifat.MyCRM.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "sanction_individual_comparison_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionIndividualComparisonHistory {
    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @Column(name = "individual_id", unique = true, nullable = false)
    private String individualId;

    @Column(name = "name_score")
    private BigDecimal nameScore;

    @Column(name = "date_of_birth_score")
    private BigDecimal dateOfBirthScore;

    @Column(name = "place_of_birth_score")
    private BigDecimal placeOfBirthScore;

    @Column(name = "address_score")
    private BigDecimal addressScore;

    @Column(name = "total_score")
    private BigDecimal totalScore;

    @Column(name = "match_status")
    private String matchStatus;

}
