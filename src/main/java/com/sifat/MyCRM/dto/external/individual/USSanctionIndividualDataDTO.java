package com.sifat.MyCRM.dto.external.individual;

import com.sifat.MyCRM.dto.external.USSanctionAddressDataDTO;
import com.sifat.MyCRM.dto.external.USSanctionAliasDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

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
    private String comments1; //other info
    private String has_interpol_link; //YES,NO
    private String interpol_link;

    private List<String> designation;
    private List<String> nationality;
    private List<String> list_type;
    private List<String> last_day_updated;
    private List<String> last_reviewed_on;

    //individual_aliases
    private List<USSanctionAliasDTO> individual_alias;
    private List<USSanctionAddressDataDTO> individual_address;
    private List<String> title;

    private List<USSanctionIndividualDOBDTO> individual_date_of_birth;
    private List<USSanctionIndividualPOBDataDTO> individual_place_of_birth;
    private List<USSanctionIndividualDocDTO> individual_document;

}
