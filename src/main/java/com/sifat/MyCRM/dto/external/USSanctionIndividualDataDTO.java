package com.sifat.MyCRM.dto.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class USSanctionIndividualDataDTO {

    private String data_id;
    private String version_no;
    private String first_name;
    private String second_name;
    private String third_name;
    private String fourth_name;

    private String un_list_type;
    private String reference_number;
    private String listed_on;
    private String name_original_script;
    private String has_interpol_link;
    private String interpol_link;
    private String comments1; //other info

    private String designation;
    private String title;

    private String list_type;
    private String last_day_updated;
    private String last_reviewed_on;

    private String nationality;
    private String date_of_birth;
    private USSanctionIndividualPOBDataDTO place_of_birth;
    private String birth_country;

    private String passport_no;
    private String nid_no;
    private String individual_address;
    private String individual_good_quality_alias;
    private String individual_low_quality_alias;

    private USSanctionIndividualDocDTO document;






}
