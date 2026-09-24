package com.sifat.MyCRM.dto.output;

import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndividualCustomerCompareResultOutDTO {
    private String customer_id;
    private String individual_compare_percentage;
    private USSanctionIndividualDataDTO top_match_with;
}
