package com.sifat.MyCRM.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Entity
@Table(name = "aml_sanction_configuration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Check(constraints = """
        after_score_name_weight
        + after_score_dob_weight
        + after_score_doc_weight
        + after_score_nationality_weight
        + after_score_address_weight
        +after_score_pob_weight = 1.00
    """
)
public class AMLSanctionConfiguration {
    @Id
    @Column(name = "id", nullable = false, length = 128)
    private String id;

    @Column(name = "only_exact_year_matched_dob_score",nullable = false)
    private double onlyExactYearMatchDobScore;

    @Column(name = "year_in_between_given_two_year_matched_dob_score",nullable = false)
    private double yearInBetweenGivenTwoYearMatchDobScore;

    @Column(name = "only_year_matched_with_approximate_year_dob_score",nullable = false)
    private double onlyYearMatchedWithApproximateYearDobScore;

    @Column(name = "after_score_name_weight",nullable = false)
    private double afterScoreNameWeight;

    @Column(name = "after_score_dob_weight",nullable = false)
    private double afterScoreDobWeight;

    @Column(name = "after_score_doc_weight",nullable = false)
    private double afterScoreDocWeight;

    @Column(name = "after_score_nationality_weight",nullable = false)
    private double afterScoreNationalityWeight;

    @Column(name = "after_score_address_weight",nullable = false)
    private double afterScoreAddressWeight;

    @Column(name = "after_score_pob_weight",nullable = false)
    private double afterScorePobWeight;

    @Column(name = "high_risk_start_score",nullable = false)
    private double highRiskStartScore;

    @Column(name = "potential_match_start_score",nullable = false)
    private double potentialMatchStartScore;

    @Column(name = "sanction_clear_till_score",nullable = false)
    private double sanctionClearTillScore;
}
