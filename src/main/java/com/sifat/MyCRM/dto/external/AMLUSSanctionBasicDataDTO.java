package com.sifat.MyCRM.dto.external;

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
public class AMLUSSanctionBasicDataDTO {

    private String full_name;
    private LocalDate date_of_birth;
    private String place_of_birth;
    private String good_quality_alias;
    private String low_quality_alias;

    private String nationality;
    private String passport_no;
    private String nid_no;
    private String address;
}
