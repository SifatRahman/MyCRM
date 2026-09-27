package com.sifat.MyCRM.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateIndividualCustomerDTO {

    @NotBlank
    private String full_name;
    private String full_name_2;
    private String family_name;
    private String short_name;

    private String mnemonic;

    private String gender;

    private Integer account_officer;
    private Integer sector;
    private Integer target;
    @NotBlank
    private Integer customer_status;

    private String industry;
    private Integer language;
    private String residence;
    @NotBlank
    private LocalDate date_of_birth;
    @NotBlank
    private String nationality;

    private String nid_no;
    private String passport_no;

    private String father_name;
    private String mother_name;

    private String marital_status;
    private String spouse;

    private String cb_sector_code;

    private LocalDate return_submission_date;

    private Boolean sms_alert_service;
    private CreateIndividualCustomerPermanentAddressDTO individual_customer_permanent_address;
}
