package com.sifat.MyCRM.dto.external.individual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class USSanctionIndividualDOBDTO {
    private String type_of_date; //EXACT, BETWEEN
    private String from_year;
    private String to_year;
    private String year;


}
