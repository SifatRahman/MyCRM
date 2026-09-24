package com.sifat.MyCRM.dto.output;

import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityCustomerCompareResultOutDTO {
    private String entity_id;
    private String entity_match_percentage;
    private String decision;
    private LocalDateTime matched_at;
    private USSanctionIndividualDataDTO top_match_with;
}