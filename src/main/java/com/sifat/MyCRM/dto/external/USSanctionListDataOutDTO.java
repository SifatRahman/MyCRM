package com.sifat.MyCRM.dto.external;

import com.sifat.MyCRM.dto.external.entity.USSanctionEntityDataDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class USSanctionListDataOutDTO {
    private List<USSanctionIndividualDataDTO> individuals;
    private List<USSanctionEntityDataDTO> entities;
}
