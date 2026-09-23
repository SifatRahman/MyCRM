package com.sifat.MyCRM.dto.external;

import com.sifat.MyCRM.dto.external.entity.USSanctionEntityDataDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import com.sifat.MyCRM.entity.SanctionEntity;
import com.sifat.MyCRM.entity.SanctionIndividual;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class USSanctionListDBOutDTO {
    private List<SanctionIndividual> individuals;
    private List<SanctionEntity> entities;
}
