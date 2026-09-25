package com.sifat.MyCRM.dto.output;

import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualCustomerCompareResultOutDTO {
    private String sanctioned_person_id;
    private String sanctioned_person_name;
    private BigDecimal name_score;
    private BigDecimal dob_score;
    private BigDecimal doc_score;
    private BigDecimal address_score;
    private BigDecimal pob_score;
    private BigDecimal nationality_score;
    private BigDecimal overall_score;
    private String individual_compare_percentage;
    private String sanction_match_status;
    //private USSanctionIndividualDataDTO top_match_with;
}
