package com.sifat.MyCRM.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class USSanctionIndividualPOBDataDTO {
    private String country;
    private String state_province;
    private String city;

}
