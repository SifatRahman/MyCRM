package com.sifat.MyCRM.dto.output;

import com.sifat.MyCRM.dto.external.entity.USSanctionEntityDataDTO;
import com.sifat.MyCRM.dto.external.individual.USSanctionIndividualDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BDSanctionListDataOutDTO {
    private Integer sl;
    private String nameOfEntity;
    private String addressOfEntity;
    private LocalDate dateOfProscription;
    private String comment;
}
