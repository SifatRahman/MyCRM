package com.sifat.MyCRM.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityCustomerCompareResultOutDTO {
    private String sanctioned_entity_id;
    private String sanctioned_entity_name;
    private BigDecimal name_score;
    private BigDecimal address_score;
    private BigDecimal overall_score;
    private String entity_compare_percentage;
    private String sanction_match_status;
}