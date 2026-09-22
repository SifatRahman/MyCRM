package com.sifat.MyCRM.dto.external.individual;

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
    private String type_of_document2;
    private String number;
    private String issuing_country;
    private String date_of_issue;
    private String date_of_expiry;
    private String city_of_issue;
    private String country_of_issue;
    private String note;

}
