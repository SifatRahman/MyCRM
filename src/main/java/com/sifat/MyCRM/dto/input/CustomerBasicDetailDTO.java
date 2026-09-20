package com.sifat.MyCRM.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBasicDetailDTO {

    //Full name
    private String full_name;
    private String full_name_2;
    private String family_name;
    private String short_name;
    private String mnemonic;

    private String gender; //male, female , third gender

    private Integer account_officer;
    private Integer sector;
    private Integer target;
    private Integer customer_status; // 1->Individual, 2-> Corporate
    private String industry;
    private Integer language; // 1->Eng, 2-> BD
    private String residence; //All BD

    private LocalDate date_of_birth;
    private String nationality;
    private String nid_no; //nid_number


    private String father_name;
    private String mother_name;
    private String marital_status;
    private String spouse;


    private String cb_sector_code;
    private String return_submission_date;
    private Boolean sms_alert_service;

}
