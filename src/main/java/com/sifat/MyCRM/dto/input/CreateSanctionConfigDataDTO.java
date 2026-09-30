package com.sifat.MyCRM.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSanctionConfigDataDTO {

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double only_exact_year_matched_dob_score;


    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double year_in_between_given_two_year_matched_dob_score;


    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double only_year_matched_with_approximate_year_dob_score;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double after_score_name_weight;


    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double after_score_dob_weight;


    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double after_score_doc_weight;


    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double after_score_nationality_weight;


    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double after_score_address_weight;


    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double after_score_pob_weight;


    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double high_risk_start_score;


    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double potential_match_start_score;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private Double sanction_clear_till_score;
}
