package com.sifat.MyCRM.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class USSanctionIndividualDocDTO {

    private String type_of_document;
    private String number;
    private String issuing_country;
    private String date_of_issue;
    private String note;

}
