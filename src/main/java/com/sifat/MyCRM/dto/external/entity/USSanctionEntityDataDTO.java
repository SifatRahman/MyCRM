package com.sifat.MyCRM.dto.external.entity;

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
public class USSanctionEntityDataDTO {
    private String data_id;
    private String version_no;
    private String first_name;
    private String un_list_type;
    private String reference_number;
    private String listed_on;
    private String name_original_script;
    private String comments1;
    private String has_interpol_link;
    private String interpol_link;
    private List<String> list_type;
    private List<String> last_day_updated;
    private List<String> last_reviewed_on;
    private List<USSanctionAliasDTO> entity_alias;
    private List<USSanctionAddressDataDTO> entity_address;
}
