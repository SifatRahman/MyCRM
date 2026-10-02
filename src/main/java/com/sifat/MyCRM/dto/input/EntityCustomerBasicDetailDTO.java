package com.sifat.MyCRM.dto.input;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityCustomerBasicDetailDTO {

    private String full_name;
    private Integer account_officer;
    private Integer sector;
    private Integer target;
    private Integer customer_status; // 1->Individual, 2-> Corporate
    private String industry;
    private Integer language; // 1->Eng, 2-> BD
    private String residence; //All BD
    private String cb_sector_code;
    private LocalDate return_submission_date;
    private Boolean sms_alert_service;
}
