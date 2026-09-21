package com.sifat.MyCRM.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class USSanctionAddressDataDTO {
    private String street;
    private String city;
    private String zip_code;
    private String country;
    private String note;
}
