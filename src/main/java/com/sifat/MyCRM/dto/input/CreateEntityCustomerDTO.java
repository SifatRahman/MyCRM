package com.sifat.MyCRM.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateEntityCustomerDTO {

    @NotBlank
    private String full_name;
    private Integer account_officer;
    private Integer sector;
    private Integer target;
    @NotNull
    private Integer customer_status;
    private String industry;
    private Integer language;
    private String residence;
    private String cb_sector_code;
    private LocalDate return_submission_date;
    private Boolean sms_alert_service;
    private CreateEntityCustomerPermanentAddressDTO entityCustomerPermanentAddressDTO;
}
