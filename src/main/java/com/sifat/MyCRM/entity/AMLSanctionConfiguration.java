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

    @Column(name = "after_score_individual_name_weight",nullable = false)
    private double afterScoreIndividualNameWeight;

    @Column(name = "after_score_individual_dob_weight",nullable = false)
    private double afterScoreIndividualDobWeight;

    @Column(name = "after_score_individual_doc_weight",nullable = false)
    private double afterScoreIndividualDocWeight;

    @Column(name = "after_score_individual_nationality_weight",nullable = false)
    private double afterScoreIndividualNationalityWeight;

    @Column(name = "after_score_individual_address_weight",nullable = false)
    private double afterScoreIndividualAddressWeight;

    @Column(name = "after_score_individual_pob_weight",nullable = false)
    private double afterScoreIndividualPobWeight;

    @Column(name = "after_score_entity_name_weight",nullable = false)
    private double afterScoreEntityNameWeight;

    @Column(name = "after_score_entity_address_weight",nullable = false)
    private double afterScoreEntityAddressWeight;

    @Column(name = "individual_high_risk_start_score",nullable = false)
    private double individualHighRiskStartScore;

    @Column(name = "individual_potential_match_start_score",nullable = false)
    private double individualPotentialMatchStartScore;

    @Column(name = "individual_sanction_clear_till_score",nullable = false)
    private double individualSanctionClearTillScore;

    @Column(name = "entity_high_risk_start_score",nullable = false)
    private double entityHighRiskStartScore;

    @Column(name = "entity_potential_match_start_score",nullable = false)
    private double entityPotentialMatchStartScore;

    @Column(name = "entity_sanction_clear_till_score",nullable = false)
    private double entitySanctionClearTillScore;
}
